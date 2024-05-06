package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalTourViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
) : ViewModel() {
    // 지역 코드, 지역기반 관광 조회 정보(지역 코드, 관광 타입)
    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    val _tourInfo = MutableStateFlow<UiState<ArrayList<AreaBasedItem>>>(UiState.Ready())
    val tourInfo = _tourInfo

    /**
     * 지역별 관광지 리스트 불러오기
     */
    fun requestAreaList() {
        viewModelScope.launch {
            serverRepo.requestAreaCode()
                .onStart { _areaInfo.value = UiState.Loading() }
                .catch { _areaInfo.value = UiState.Error(it.message!!) }
                .collect {
                    if(!it.data.isNullOrEmpty()){
                        _areaInfo.value = it
                    }else{
                        // empty 일때
                    }
                }
        }
    }

    /**
     * 지역별 관광지 리스트 불러오기
     *
     * @param areaCode 지역 코드
     */
    fun requestTourInfo(areaCode: String? = "") {
        var code = areaCode

        viewModelScope.launch {
            serverRepo.requestAreaBasedList(code,)
                .onStart { _tourInfo.value = UiState.Loading() }
                .catch { _tourInfo.value = UiState.Error(it.message!!) }
                .collect { _tourInfo.value = it }
        }
    }

}