package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
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
class LocalTourViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
) : ViewModel() {
    // 지역 코드, 지역기반 관광 조회 정보(지역 코드, 관광 타입)
    val _areaInfo = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaInfo = _areaInfo

    val _tourInfo = MutableStateFlow<UiState<ArrayList<StayItem>>>(UiState.Ready())
    val tourInfo = _tourInfo

    /**
     * 지역별 관광지 리스트 불러오기
     */
    // TODO AreaList 불러 오는거 HomeWidget 에서 공통 처리 후 진행 했으면 좋겠다. (둘 다 사용 하기 때문)
    fun requestAreaList() {
        viewModelScope.launch {
            serverRepo.requestAreaCode()
                .onStart { _areaInfo.value = UiState.Loading() }
                .catch { _areaInfo.value = UiState.Error(it.message!!) }
                .collect { _areaInfo.value = it }
        }
    }

    /**
     * 지역별 관광지 리스트 불러오기
     *
     * @param areaName 지역 이름
     * @param areaCode 지역 코드
     */
    fun requestTourInfo(areaName: String? = "", areaCode: String? = "") {
        var code = areaCode

        if (areaName.isNotNullOrEmpty()) {
            val isValidArea = ServerGlobal.isValidAreaName(areaName!!)
            if (isValidArea) {
                code = ServerGlobal.getAreaName(areaName)
            } else {
                // 지역 이름 값이 잘못 들어갈 때 예외 처리
                return
            }
        }
        viewModelScope.launch {
            serverRepo.requestStayInfo(code)
                .onStart { _tourInfo.value = UiState.Loading() }
                .catch { _tourInfo.value = UiState.Error(it.message!!) }
                .collect { _tourInfo.value = it }
        }
    }

}