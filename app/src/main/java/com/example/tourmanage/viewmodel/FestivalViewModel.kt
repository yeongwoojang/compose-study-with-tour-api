package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import com.example.tourmanage.usecase.data.common.GetLocationBasedUseCaseImpl
import com.example.tourmanage.usecase.domain.common.GetLocationBasedUseCase
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
    private val getFestivalUseCase: GetFestivalUseCase,
    private val getLocationBasedUseCase: GetLocationBasedUseCase
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("exceptionHandler:: | throwable: $throwable")
    }

    private val _festivalInfo = MutableStateFlow<UiState<Festival>>(UiState.Ready())
    val festivalInfo = _festivalInfo

    private val clickItemFlow = MutableSharedFlow<Any>()

    val goDetailFlow = clickItemFlow
        .flatMapLatest { item ->
            flow<DetailFestival> {
                emit(DetailFestival().apply {
                    if (item is FestivalItem) {
                        title = item.title
                    } else if (item is LocationBasedItem) {
                        title = item.title
                    }
                })
            }
        }

    init {
        load()
    }

    private fun load() {
        requestFestival(Config.CONTENT_TYPE_ID.FESTIVAL)
    }

    fun requestFestival(typeId: Config.CONTENT_TYPE_ID) {
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
            val locationBasedFestivalFlow= locationDeferred.await()
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

    fun choiceFestival(item: Any) {
        viewModelScope.launch {
            clickItemFlow.emit(item)
        }
    }
}

data class Festival(
    var recommendFestival: ArrayList<FestivalItem> = ArrayList(emptyList()),
    var localFestival: ArrayList<LocationBasedItem> = ArrayList(emptyList())
)

data class DetailFestival(
    var title: String? = "",
    var contentId: String? = "",
    var addr1: String? = "",
    var addr2: String? = "",
    var contentTypeId: String? = "",
    var mainImage: String? = ""
)