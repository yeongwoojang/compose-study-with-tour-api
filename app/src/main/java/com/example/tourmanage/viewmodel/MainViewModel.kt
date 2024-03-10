package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.toAreaInfoList
import com.example.tourmanage.toStayInfoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: ServiceAPI
): ViewModel() {
    //_ viewModel은 Activity lifeCycle을 따르기 때문에 remember 필요x
    //_ Activity에서 mutable변수에 remember를 사용하는 이유는 re-composition이 일어날 때 변수가 초기화되기 때문
    //_ val data = mutableStateOf("TEST")

    val _stayInfo = MutableStateFlow<UiState<ArrayList<StayItem>>>(UiState.Ready())
    val stayInfo = _stayInfo

    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    fun requestStayInfo() {
        Timber.i("requestStayInfo() is called.")
        viewModelScope.launch {
            val stayInfo = client.requestSearchStay()
            val code = stayInfo.response?.header?.resultCode
            val msg = stayInfo.response?.header?.resultMsg
            val stayItemList =  stayInfo.toStayInfoList()
            if ("0000" == code && stayItemList.isNotEmpty()) {
                _stayInfo.value = UiState.Success(stayItemList)
            } else {
                _stayInfo.value = UiState.Error(msg ?: "requestStayInfo() Error.")
            }
        }
    }

    fun requestAreaList() {
        Timber.i("requestAreaList() is called.")
        viewModelScope.launch {
            val areaInfo = client.requestAreaList()
            val code = areaInfo.response?.header?.resultCode
            val msg = areaInfo.response?.header?.resultMsg
            val areaItemList = areaInfo.toAreaInfoList()
            if ("0000" == code) {
                ServerGlobal.setAreaCodeMap(areaItemList)
                _areaInfo.value = UiState.Success(areaItemList)
            } else {
                _areaInfo.value = UiState.Error(msg ?: "requestAreaList() Error.")
            }
        }
    }
}