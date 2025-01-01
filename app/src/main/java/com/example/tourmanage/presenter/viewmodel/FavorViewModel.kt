package com.example.tourmanage.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.domain.favor.GetAllFavorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavorViewModel @Inject constructor(
     private val getAllFavorUseCase: GetAllFavorUseCase
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("exceptionHandler:: | throwable: $throwable")

    }

    private val _favorDataFlow = MutableStateFlow<UiState<List<FavorEntity>>>(UiState.Loading())
    val favorDataFlow = _favorDataFlow.asStateFlow()

    fun getFavorAll() {
        Timber.i("getFavorAll()")
       viewModelScope.launch(exceptionHandler) {
           _favorDataFlow.value = UiState.Loading()
           val data = getAllFavorUseCase().getOrThrow()
           _favorDataFlow.value = UiState.Success(data)
       }

    }
}