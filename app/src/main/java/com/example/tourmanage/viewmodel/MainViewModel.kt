package com.example.tourmanage.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.setDefaultCollect
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
): CommonViewModel(serverRepo, dataStore) {

    private val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo.asStateFlow()

    private var _curParent = MutableSharedFlow<AreaItem?>()
    val curParent = _curParent.asSharedFlow()

    private var _curChild = MutableSharedFlow<AreaItem?>()
    val curChild = _curChild.asSharedFlow()

    val childAreaList = _curParent
        .flatMapLatest {
            serverRepo.requestAreaCode(it?.code)
        }

    fun requestParentAreaList() {
        Timber.i("requestAreaList() is called.")
        viewModelScope.launch {
            serverRepo.requestAreaCode(isInit = true)
                .setDefaultCollect(_areaInfo)
        }
    }

    private fun removeCacheArea(isChild: Boolean) {
        viewModelScope.launch {
            dataStore.removeCacheArea(isChild)
            _curChild.emit(null)
        }
    }
    fun cacheArea(areaItem: AreaItem?, isChild: Boolean = false) {
        viewModelScope.launch {
            if (areaItem == null) {
                return@launch
            }
            if (isChild) {
                _curChild.emit(areaItem)
            } else {
                _curParent.emit(areaItem)
                removeCacheArea(true)
            }
            dataStore.cacheArea(isChild, areaItem).collectLatest {
                if (it is UiState.Success) {
                    Timber.i("cacheArea() | OnSuccess | areaItem: $areaItem | isChild: $isChild")
                } else {
                    Timber.i("cacheArea() | OnFailure | areaItem: $areaItem | isChild: $isChild")

                }
            }
        }
    }

    fun getCachedArea() {
        viewModelScope.launch {
            repeat(2) { count -> //_ 부모, 자식 지역 코드 GET 하기위해 2번 조회
                val isChild = count == 1
                dataStore.getCachedArea(isChild).collectLatest {
                    if (!isChild) {
                        _curParent.emit(it.data)
                    } else {
                        _curChild.emit(it.data)
                    }
                }
            }
        }
    }
}