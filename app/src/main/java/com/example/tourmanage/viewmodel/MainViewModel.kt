package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.data.StayItem
import com.example.tourmanage.toStayInfoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: ServiceAPI
): ViewModel() {
    //_ viewModel은 Activity lifeCycle을 따르기 때문에 remember 필요x
    //_ Activity에서 mutable변수에 remember를 사용하는 이유는 re-composition이 일어날 때 변수가 초기화되기 때문
    //_ val data = mutableStateOf("TEST")

    val _stayInfo = MutableStateFlow<UiState<ArrayList<StayItem>>>(UiState.Ready())
    val stayInfo: StateFlow<UiState<ArrayList<StayItem>>> = _stayInfo

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
                _stayInfo.value = UiState.Error(msg ?: "requestStayInfo() Error")
            }
        }
    }
}