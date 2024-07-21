package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.room.FavorDatabase
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.usecase.domain.favor.GetAllFavorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
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

    private val _favorDataFlow = MutableStateFlow<UiState<List<FavorEntity>>>(UiState.Ready())
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