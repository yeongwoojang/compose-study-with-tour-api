package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StayViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
) : ViewModel() {
    private var areaRequestJob: Job? = null

    val _stayInfo = MutableStateFlow<UiState<ArrayList<StayItem>>>(UiState.Ready())
    val stayInfo = _stayInfo

    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    fun requestAreaList(areaCode: String?) {
        Timber.i("requestAreaList() | areaCode: $areaCode")
        if (areaCode != null) {
            areaRequestJob?.cancel() // 이전에 실행 중인 요청 취소
            areaRequestJob = viewModelScope.launch {
                delay(200)
                serverRepo.requestAreaCode(areaCode = areaCode)
                    .catch { _areaInfo.value = UiState.Error(it.message!!) }
                    .collect { _areaInfo.value = it }
            }
        }
    }

    fun requestStayList(areaCode: String?, sigunguCode: String?) {
        Timber.i("requestStayList() | areaCode: $areaCode | sigunguCode: $sigunguCode")
        if (areaCode == null || sigunguCode == null) {
            return
        }

        viewModelScope.launch {
            serverRepo.requestStayInfo(areaCode, sigunguCode)
                .onStart { _stayInfo.value = UiState.Loading() }
                .catch { _stayInfo.value = UiState.Error(it.message!!) }
                .collect { _stayInfo.value = it}
        }
    }

    override fun onCleared() {
        areaRequestJob?.cancel()
        super.onCleared()
    }
}