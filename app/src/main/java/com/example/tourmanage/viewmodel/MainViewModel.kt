package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.StayDetailItem
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

    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    val _stayDetailInfo = MutableStateFlow<UiState<StayDetailItem>>(UiState.Ready())
    val stayDetailInfo = _stayDetailInfo

    fun requestAreaList() {
        Timber.i("requestAreaList() is called.")
        viewModelScope.launch {
            serverRepo.requestAreaCode(isInit = true)
                .onStart { _areaInfo.value = UiState.Loading()}
                .catch { _areaInfo.value = UiState.Error(it.message!!) }
                .collect { _areaInfo.value = it }
        }
    }

    fun requestStayDetailInfo(contentId: String?, contentType: String?) {
        Timber.i("requestStayDetailInfo | contentId: $contentId | contentType: $contentType")
        if (contentId == null || contentType == null) {
            return
        }
        viewModelScope.launch {
            serverRepo.requestStayDetailInfo(contentId, contentType)
                .onStart {}
                .catch { _stayDetailInfo.value = UiState.Error(it.message!!) }
                .collect { _stayDetailInfo.value = it}
        }
    }
}
















