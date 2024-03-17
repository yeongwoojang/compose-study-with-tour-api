package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    //_ val data = mutableStateOf("TEST")

    val _stayInfo = MutableStateFlow<UiState<ArrayList<StayItem>>>(UiState.Ready())
    val stayInfo = _stayInfo

    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    fun requestStayInfo(areaName: String? = "", areaCode: String? = "") {
        Timber.i("areaName: $areaName")
        if (areaName != null) {
            val a = ServerGlobal.getAreaName(areaName)
            Timber.i("${a}")
        }
        Timber.i("requestStayInfo() is called.")
        viewModelScope.launch {
            serverRepo.requestStayInfo(areaCode)
                .onStart { _stayInfo.value = UiState.Loading() }
                .catch { _stayInfo.value = UiState.Error(it.message!!) }
                .collect { _stayInfo.value = it }
        }
    }

    fun requestAreaList() {
        Timber.i("requestAreaList() is called.")
        viewModelScope.launch {
            serverRepo.requestAreaCode()
                .onStart { _areaInfo.value = UiState.Loading()}
                .catch { _areaInfo.value = UiState.Error(it.message!!) }
                .collect { _areaInfo.value = it }
        }
    }
}