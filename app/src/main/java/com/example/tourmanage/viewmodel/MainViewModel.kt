package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.setDefaultCollect
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
): CommonViewModel(serverRepo, dataStore) {
    private var areaRequestJob: Job? = null

    private val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    private val _childAreaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val childAreaInfo = _childAreaInfo

    private val _curParentArea = MutableStateFlow<UiState<AreaItem?>>(UiState.Ready())
    val curParentArea = _curParentArea

    private val _curChildArea = MutableStateFlow<UiState<AreaItem?>>(UiState.Ready())
    val curChildArea = _curChildArea

    fun requestParentAreaList() {
        Timber.i("requestAreaList() is called.")
        viewModelScope.launch {
            serverRepo.requestAreaCode(isInit = true)
                .setDefaultCollect(_areaInfo)
        }
    }

    fun requestChildAreaList(areaCode: String?) {
        Timber.i("requestChildAreaList() | areaCode: $areaCode")
        if (areaCode != null) {
            areaRequestJob?.cancel() // 이전에 실행 중인 요청 취소
            areaRequestJob = viewModelScope.launch {
                delay(200)
                serverRepo.requestAreaCode(areaCode = areaCode)
                    .catch { _childAreaInfo.value = UiState.Error(it.message!!) }
                    .collect { _childAreaInfo.value = it }
            }
        }
    }

    fun removeCacheArea(isChild: Boolean) {
        viewModelScope.launch {
            dataStore.removeCacheArea(isChild)
            _curChildArea.value = UiState.Success(null)
        }
    }
    fun cacheArea(areaItem: AreaItem?, isChild: Boolean = false) {
        viewModelScope.launch {
            if (areaItem == null) {
                return@launch
            }
            val data = if (isChild) _curChildArea else _curParentArea
            dataStore.cacheArea(isChild, areaItem).setDefaultCollect(data)
        }
    }

    fun getCachedArea() {
        viewModelScope.launch {
            repeat(2) { count -> //_ 부모, 자식 지역 코드 GET 하기위해 2번 조회
                val data = if (count == 1) _curChildArea else _curParentArea
                val isChild = count == 1
                dataStore.getCachedArea(isChild)
                    .setDefaultCollect(data)
            }
        }
    }

    override fun onCleared() {
        areaRequestJob?.cancel()
        super.onCleared()
    }
}