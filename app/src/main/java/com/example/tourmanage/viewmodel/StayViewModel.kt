package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.extension.isNotNullOrEmpty
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StayViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
) : ViewModel() {
    val _stayInfo = MutableStateFlow<UiState<ArrayList<StayItem>>>(UiState.Ready())
    val stayInfo = _stayInfo


    fun requestStayInfo(areaName: String? = "", areaCode: String? = "") {
        var code = areaCode
        Timber.i("areaName: $areaName")
        if (areaName.isNotNullOrEmpty()) {
            val isValidArea = ServerGlobal.isValidAreaName(areaName!!)
            Timber.i("isValidArea: $isValidArea")
            if (isValidArea) {
                Timber.i("requestStayInfo() | $code")
                code = ServerGlobal.getAreaName(areaName)
            } else {
                _stayInfo.value = UiState.Error("INVALID_AREA")
                return
            }
        }
        Timber.i("requestStayInfo() is called.")
        viewModelScope.launch {
            serverRepo.requestStayInfo(code)
                .onStart { _stayInfo.value = UiState.Loading() }
                .catch { _stayInfo.value = UiState.Error(it.message!!) }
                .collect { _stayInfo.value = it }
        }
    }

}