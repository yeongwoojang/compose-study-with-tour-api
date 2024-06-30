package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.error.area.AreaException
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val getAreaUseCase: GetAreaUseCase
): ViewModel() {
    private val _areaCodesState = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Ready())
    val areaCodesState = _areaCodesState.asStateFlow()

    private val _exceptionState = MutableSharedFlow<Throwable>()
    val exceptionState = _exceptionState.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _exceptionState.emit(throwable)
        }
    }
    fun getAreaList() {
        viewModelScope.launch(exceptionHandler) {
            getAreaUseCase().getOrThrow()
                .onStart { _areaCodesState.value = UiState.Loading() }
                .collect{ _areaCodesState.value = UiState.Success(it) }
        }
    }

}