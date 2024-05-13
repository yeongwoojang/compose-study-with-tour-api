package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.setDefaultCollect
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
): ViewModel() {
    private var areaRequestJob: Job? = null

    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    val _childAreaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val childAreaInfo = _childAreaInfo

    private val _curParentArea = MutableStateFlow<UiState<AreaItem?>>(UiState.Ready())
    val curParentArea = _curParentArea

    private val _curChildArea = MutableStateFlow<UiState<AreaItem?>>(UiState.Ready())
    val curChildArea = _curChildArea

    fun requestParentAreaList() {
        Timber.i("requestAreaList() is called.")
        viewModelScope.launch {
            serverRepo.requestAreaCode(isInit = true)
                .onStart { _areaInfo.value = UiState.Loading()}
                .catch { _areaInfo.value = UiState.Error(it.message!!) }
                .collect { _areaInfo.value = it }
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

    fun getCacheArea(isChild: Boolean = false) {
        viewModelScope.launch {
            val data = if (isChild) _curChildArea else _curParentArea
            dataStore.getCachedArea(isChild)
                .onStart { data.value = UiState.Loading() }
                .catch {
                    data.value = UiState.Error("")
                }
                .collect {
                    data.value = it
                }
        }
    }

    override fun onCleared() {
        areaRequestJob?.cancel()
        super.onCleared()
    }
}
















