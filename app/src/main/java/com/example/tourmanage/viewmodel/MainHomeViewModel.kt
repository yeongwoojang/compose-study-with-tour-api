package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainHomeViewModel @Inject constructor(
    private val getAreaUseCase: GetAreaUseCase,
    private val cacheAreaUseCase: CacheAreaUseCase
): ViewModel() {
     private var getChildAreaJob: Job? = null //_ 버튼을 연타하여 연속적인 조회를 막고 이전 조회는 취소하기 위한 Job

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("exceptionHandler:: | throwable: $throwable")
    }

    private val _childAreaCodesState = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val childAreaCodesState = _childAreaCodesState.asStateFlow()
    fun cacheArea(areaItem: AreaItem?, isChild: Boolean = false) {
        getChildAreaJob?.cancel() //_ job을 취소하면 해당 코루틴 내에서 동작중인 비동기 작업도 취소됨. 따라서 서버 조회가 취소됨.
        getChildAreaJob = viewModelScope.launch(exceptionHandler) {
            if (areaItem == null) {
                return@launch
            }
            val result = cacheAreaUseCase(areaItem, isChild).getOrThrow()
            if (result) {
                Timber.i("cacheArea is $result")
                getAreaUseCase(areaItem.code).getOrThrow()
                    .collect{ _childAreaCodesState.value = UiState.Success(it) }
            }
        }
    }
}