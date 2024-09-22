package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.usecase.domain.common.GetTourInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StayViewModel @Inject constructor(
    private val getTourInfoUseCase: GetTourInfoUseCase
): ViewModel() {

    private val _tourFlow = MutableStateFlow<PagingData<PosterItem>>(PagingData.empty())
    val tourFlow = _tourFlow.onStart {
        requestTourInfo()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), PagingData.empty())

    private fun requestTourInfo() {
        viewModelScope.launch {
            getTourInfoUseCase(
                contentTypeId = Config.CONTENT_TYPE_ID.TOUR_COURSE
            ).getOrThrow().collect { pagingData ->
                _tourFlow.value = pagingData
            }
        }
    }
}