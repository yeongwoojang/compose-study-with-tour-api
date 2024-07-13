package com.example.tourmanage.viewmodel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.model.ServerDataRepository
import com.example.tourmanage.usecase.data.common.GetDetailCommonUseCase
import com.example.tourmanage.usecase.data.common.GetDetailImageUseCase
import com.example.tourmanage.usecase.data.common.GetDetailInfoUseCase
import com.example.tourmanage.usecase.domain.common.GetLocationBasedUseCase
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val getFestivalUseCase: GetFestivalUseCase,
    private val getLocationBasedUseCase: GetLocationBasedUseCase,
    private val getDetailInfoUseCase: GetDetailInfoUseCase,
    private val getDetailCommonUseCase: GetDetailCommonUseCase,
    private val getDetailImageUseCase: GetDetailImageUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("exceptionHandler:: | throwable: $throwable")
    }

    private val _festivalInfo = MutableStateFlow<UiState<Festival>>(UiState.Ready())
    val festivalInfo = _festivalInfo.asStateFlow()

    private val _festivalDetailInfo = MutableStateFlow<UiState<FestivalDetail>>(UiState.Loading())
    val festivalDetailInfo = _festivalDetailInfo.asStateFlow()

    init {
        load()
    }

    private fun load() {
        requestFestival(Config.CONTENT_TYPE_ID.FESTIVAL)
    }

    private fun requestFestival(typeId: Config.CONTENT_TYPE_ID) {
        Timber.i("requestFestival() typeId: $typeId")
        viewModelScope.launch(exceptionHandler) {
            val currentGPS = ServerGlobal.getCurrentGPS()
            val longitude = currentGPS.mapX
            val latitude = currentGPS.mapY

            val recommendDeferred = async {
                getFestivalUseCase(areaCode = "6").getOrThrow()
            }

            val locationDeferred = async {
                getLocationBasedUseCase(
                    contentTypeId = typeId,
                    mapX = longitude,
                    mapY = latitude
                ).getOrThrow()
            }

            val recommendFestivalFlow = recommendDeferred.await()
            val locationBasedFestivalFlow = locationDeferred.await()

            recommendFestivalFlow.combine(locationBasedFestivalFlow) { recommend, local ->
                Festival().apply {
                    if (recommend.isNotEmpty()) this.recommendFestival = recommend
                    if (local.isNotEmpty()) this.localFestival = local
                }
            }.onStart {
                _festivalInfo.value = UiState.Loading()
            }.collect {
                _festivalInfo.value = UiState.Success(it)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun requestFestivalDetail(contentId: String?) {
        contentId ?: return
        Timber.i("requestFestivalDetail | $contentId")
        val festivalDetailFlow = MutableSharedFlow<FestivalDetail>()

        viewModelScope.launch {
            val festivalDetail = FestivalDetail()

            launch {
                festivalDetailFlow
                    .flatMapLatest {
                        flow {
                            delay(500)
                            emit(it)
                        }
                    }
                    .onStart { _festivalDetailInfo.value = UiState.Loading() }
                    .collectLatest {
                        _festivalDetailInfo.value = UiState.Success(it)
                }
            }

            val detailInfoFlow = async { getDetailInfoUseCase(contentId, Config.CONTENT_TYPE_ID.FESTIVAL).getOrThrow() }
            val detailCommonFlow = async { getDetailCommonUseCase(contentId, Config.CONTENT_TYPE_ID.FESTIVAL).getOrThrow() }
            val detailImageFlow = async { getDetailImageUseCase(contentId).getOrThrow() }

            detailInfoFlow.await()
                .catch {
                    festivalDetailFlow.emit(festivalDetail.apply {
                        overview = ""
                        infoText = ""
                    })
                }
                .collect {
                    festivalDetailFlow.emit(festivalDetail.apply {
                        overview = it[0]?.infoText.orEmpty()
                        infoText = it[1]?.infoText.orEmpty()
                    })
                }
            detailCommonFlow.await()
                .catch {
                    festivalDetailFlow.emit(festivalDetail.apply {
                        mainImage = ""
                        homePageUrl = ""
                        tel = ""
                        mapX = ""
                        mapY = ""
                        addr1 = ""
                        addr2 = ""
                    })
                }
                .collect {
                    festivalDetailFlow.emit(festivalDetail.apply {
                        mainImage = it?.mainImage.orEmpty()
                        homePageUrl = it?.hompageUrl.orEmpty()
                        tel = it?.tel.orEmpty()
                        mapX = it?.mapX.orEmpty()
                        mapY = it?.mapY.orEmpty()
                        addr1 = it?.addr1.orEmpty()
                        addr2 = it?.addr2.orEmpty()
                    })

                }
            detailImageFlow.await()
                .catch {
                    festivalDetailFlow.emit(festivalDetail.apply {
                        images = ArrayList(emptyList())
                    })
                }
                .collect {
                    festivalDetailFlow.emit(festivalDetail.apply {
                        images = it
                    })
                }
        }

    }
}

data class Festival(
    var recommendFestival: ArrayList<FestivalItem> = ArrayList(emptyList()),
    var localFestival: ArrayList<LocationBasedItem> = ArrayList(emptyList())
)


data class FestivalArgument(
    var title: String? = "",
    var contentId: String? = "",
    var addr1: String? = "",
    var addr2: String? = "",
    var contentTypeId: String? = "",
    var mainImage: String? = ""
)

data class FestivalDetail(
    var mainImage: String = "",
    var overview: String = "",
    var infoText: String = "",
    var homePageUrl: String = "",
    var tel: String = "",
    var mapX: String = "",
    var mapY: String = "",
    var addr1: String = "",
    var addr2: String = "",
    var images: ArrayList<DetailImageItem> = ArrayList(emptyList())
)