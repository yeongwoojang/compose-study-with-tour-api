package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetCacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.RemoveCacheAreaUseCase
import com.example.tourmanage.usecase.domain.common.GetTourInfoUseCase
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase2
import com.example.tourmanage.usecase.domain.stay.GetStayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAreaUseCase: GetAreaUseCase,
    private val cacheAreaUseCase: CacheAreaUseCase,
    private val getCacheAreaUseCase: GetCacheAreaUseCase,
    private val removeCacheAreaUseCase: RemoveCacheAreaUseCase,
    private val getFestivalUseCase: GetFestivalUseCase2,
    private val getStayUseCase: GetStayUseCase,
    private val getTourInfoUseCase: GetTourInfoUseCase

): ViewModel() {
    private val ceh = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is TourMangeException.GetFestivalException -> {
            }
        }
        Timber.e("exceptionHandler:: | throwable: $throwable")
    }

    private val _subAreaList = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Loading())
    val subAreaList = _subAreaList.asStateFlow()

    private val _currentArea = MutableStateFlow<UiState<Pair<AreaItem?, AreaItem?>>>(UiState.Loading())
    val currentArea = _currentArea.asStateFlow()

    private val _selectedArea = MutableStateFlow<Pair<AreaItem?, Boolean>>(Pair(null, false))
    private val selectedArea = _selectedArea.filter { it.first != null }.debounce(500).onEach {
        val selectedArea = it.first!!
        val isSigungu = it.second
        cacheArea(
            areaItem = selectedArea,
            isSigungu = isSigungu
        )
    }.launchIn(viewModelScope + ceh)

    private val _posterFlow = MutableStateFlow<UiState<List<PosterItem>>>(UiState.Loading())
    val posterFlow = _posterFlow.onStart { //_ collect를 시작하는 시점에 onStart가 실행됨.
        fetchAllData()
    }.stateIn(
        viewModelScope + ceh,
        SharingStarted.WhileSubscribed(5000L),
        UiState.Loading())


    private fun cacheArea(areaItem: AreaItem, isSigungu: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO + ceh) {
            val result = cacheAreaUseCase(areaItem, isSigungu).getOrThrow()
            if (result) {
                if (!isSigungu) {
                    removeCacheAreaUseCase(true).getOrThrow()
                }
            }
            updateArea().collect {
                val updateStayInfo = requestStayInfo(areaCode = it.first, sigunguCode = it.second)
                val posterItem = _posterFlow.value.data!!.filterNot { it.contentTypeId == Config.CONTENT_TYPE_ID.STAY.value }
                _posterFlow.value = UiState.Success(posterItem.plus(updateStayInfo))
            }
        }
    }

    fun onChangeArea(areaItem: AreaItem, isSigungu: Boolean) {
        viewModelScope.launch {
            _selectedArea.value = Pair(areaItem, isSigungu)
        }
    }

    private suspend fun getCachedArea(): Flow<Pair<AreaItem?, AreaItem?>> {
        return flow {
            val areaItem = getCacheAreaUseCase(isSub = false).getOrThrow()
            val sigunguItem = getCacheAreaUseCase(isSub = true).getOrThrow()
            areaItem.onEach {
                val areaCode: ArrayList<AreaItem> = getAreaUseCase(it?.code).getOrThrow()
                _subAreaList.value = UiState.Success(areaCode)
            }.combine(sigunguItem) { areaItem, sigunguItem ->
                Pair(areaItem, sigunguItem)
            }.collect {
                emit(it)
            }
        }
    }

    private fun fetchAllData() {
        viewModelScope.launch(ceh) {
            //_ 시간 단축을 위해 병렬 처리
            _posterFlow.value = UiState.Loading()
            updateArea().collect {
                val festivalDef = async { getMainFestival() }
                val stayDef = async { requestStayInfo(
                    areaCode = it.first,
                    sigunguCode = it.second
                ) }
                _posterFlow.value = UiState.Success(festivalDef.await().plus(stayDef.await()))

            }
        }
    }

    private suspend fun updateArea(): Flow<Pair<String, String>> {
        return getCachedArea().map {
            _currentArea.value = UiState.Success(it)
            val areaCode = it.first?.code.orEmpty()
            val sigunguCode = it.second?.code.orEmpty()
            Pair(areaCode, sigunguCode)
        }
    }

    private suspend fun getMainFestival(): List<PosterItem> {
        return getFestivalUseCase(
            areaCode = "",
            startDate = ""
        ).getOrThrow()
    }

    private suspend fun requestStayInfo(areaCode: String?, sigunguCode: String?): List<PosterItem> {
        Timber.i("requestStayInfo() | areaCode: $areaCode | sigunguCode: $sigunguCode")
        return getStayUseCase(areaCode, sigunguCode).getOrThrow()
    }
}