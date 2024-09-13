package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetCacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.RemoveCacheAreaUseCase
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase
import com.example.tourmanage.usecase.domain.stay.GetStayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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
    private val getFestivalUseCase: GetFestivalUseCase,
    private val getStayUseCase: GetStayUseCase

): ViewModel() {
    private val ceh = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is TourMangeException.GetFestivalException -> {
            }
        }
        Timber.e("exceptionHandler:: | throwable: $throwable")
    }

    private val _festivalList = MutableStateFlow<UiState<ArrayList<FestivalItem>>>(UiState.Loading())
    val festivalList = _festivalList.asStateFlow()

    private val _stayList = MutableStateFlow<UiState<List<StayItem>>>(UiState.Loading())
    val stayList = _stayList.asStateFlow()

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

    init {
        fetchAllData()
    }

    private fun cacheArea(areaItem: AreaItem, isSigungu: Boolean = false) {
        viewModelScope.launch(ceh) {
            val result = cacheAreaUseCase(areaItem, isSigungu).getOrThrow()
            if (result) {
                if (!isSigungu) {
                    removeCacheAreaUseCase(true).getOrThrow()
                }
            }
            updateArea()
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
            //_ 시간 단축을 위해 병령 처리
            launch { getMainFestival() }
            launch { updateArea() }
        }
    }

    private suspend fun updateArea() {
        getCachedArea().collect {
            _currentArea.value = UiState.Success(it)
            val areaCode = it.first?.code
            val sigunguCode = it.second?.code
            requestStayInfo(
                areaCode = areaCode,
                sigunguCode = sigunguCode
            )
        }
    }

    private suspend fun getMainFestival() {
        getFestivalUseCase(
            areaCode = "",
            startDate = ""
        ).getOrThrow()
            .onStart {
                _festivalList.value = UiState.Loading()
            }
            .collect {
                _festivalList.value = UiState.Success(it)
            }
    }

    private suspend fun requestStayInfo(areaCode: String?, sigunguCode: String?) {
        Timber.i("requestStayInfo() | areaCode: $areaCode | sigunguCode: $sigunguCode")
        getStayUseCase(areaCode, sigunguCode).getOrThrow()
            .collect {
                _stayList.value = UiState.Success(it)
            }
    }
}