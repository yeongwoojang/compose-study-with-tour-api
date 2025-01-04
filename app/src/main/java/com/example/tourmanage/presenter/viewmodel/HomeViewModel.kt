package com.example.tourmanage.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.domain.area.CacheAreaUseCase
import com.example.tourmanage.domain.area.GetAreaUseCase
import com.example.tourmanage.domain.area.GetCacheAreaUseCase
import com.example.tourmanage.domain.area.RemoveCacheAreaUseCase
import com.example.tourmanage.domain.common.GetAreaBasedUseCase
import com.example.tourmanage.error.area.TourMangeException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
    private val getAreaBasedUseCase: GetAreaBasedUseCase,

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

    private val _posterListFLow = MutableStateFlow<PagingData<PosterItem>>(PagingData.empty())
    val posterListFlow = _posterListFLow.onStart {
        fetchAllData()
    }.cachedIn(viewModelScope)

    private val _menuFlow = MutableStateFlow(Config.CONTENT_TYPE_ID.FESTIVAL)

    fun changeMenu(menu: Config.CONTENT_TYPE_ID) {
        _menuFlow.value = menu
    }

    private fun requestPosterList(contentTypeId: Config.CONTENT_TYPE_ID) {
        viewModelScope.launch {
            val currentAreaInfo = _currentArea.value.data
            val areaCode = currentAreaInfo?.first?.code ?: ""
            val sigunguCode = currentAreaInfo?.second?.code ?: ""
            getAreaBasedUseCase(
                areaCode = areaCode,
                sigunguCode = sigunguCode,
                contentTypeId = contentTypeId
            ).getOrThrow().collect { pagingData ->
                _posterListFLow.value = pagingData
            }
        }
    }

    private fun cacheArea(areaItem: AreaItem, isSigungu: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO + ceh) {
            val result = cacheAreaUseCase(areaItem, isSigungu).getOrThrow()
            if (result) {
                if (!isSigungu) {
                    removeCacheAreaUseCase(true).getOrThrow()
                }
            }
            updateArea().collect {
                requestPosterList(contentTypeId = _menuFlow.value)
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
            updateArea().collect {
                _menuFlow.debounce(300L).onEach { currentMenu ->
                    requestPosterList(contentTypeId = currentMenu)
                }.launchIn(viewModelScope + ceh)
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
}