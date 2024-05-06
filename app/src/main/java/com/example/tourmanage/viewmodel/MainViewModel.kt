package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
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
    private val serverRepo: ServerDataRepository
): ViewModel() {
    //_ viewModel은 Activity lifeCycle을 따르기 때문에 remember 필요x
    //_ Activity에서 mutable변수에 remember를 사용하는 이유는 re-composition이 일어날 때 변수가 초기화되기 때문

    private var areaRequestJob: Job? = null


    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    val _childAreaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val childAreaInfo = _childAreaInfo


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
        Timber.i("requestAreaList() | areaCode: $areaCode")
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

    override fun onCleared() {
        areaRequestJob?.cancel()
        super.onCleared()
    }
}
















