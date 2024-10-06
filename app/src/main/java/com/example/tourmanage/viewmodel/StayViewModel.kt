package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.usecase.data.common.GetDetailCommonUseCase
import com.example.tourmanage.usecase.data.common.GetDetailImageUseCase
import com.example.tourmanage.usecase.data.common.GetDetailInfoUseCase
import com.example.tourmanage.usecase.domain.common.GetDetailIntroUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class StayViewModel @AssistedInject constructor(
    private val getDetailInfoUseCase: GetDetailInfoUseCase,
    private val getDetailCommonUseCase: GetDetailCommonUseCase,
    private val getDetailImageUseCase: GetDetailImageUseCase,
    private val getDetailIntroUseCase: GetDetailIntroUseCase,
    @Assisted private val contentId: String,
) : ViewModel() {
    private val _stayDataFlow = MutableStateFlow<UiState<List<DetailImageItem>>>(UiState.Loading())
    val stayDataFlow = _stayDataFlow.asStateFlow()

    init {
        fetchData()
    }


    private fun fetchData() {
        viewModelScope.launch {
            _stayDataFlow.value = UiState.Loading()
            val data = getDetailImageUseCase(contentId).getOrNull() ?: emptyList<DetailImageItem>()
            _stayDataFlow.value = UiState.Success(data)
        }
    }

    fun requestDetailIntro(contentId: String) {
        viewModelScope.launch {
            getDetailIntroUseCase(
                contentId = contentId,
                contentTypeId = Config.CONTENT_TYPE_ID.STAY
            )
        }
    }

    fun requestDetailCommonInfo(contentId: String) {
        viewModelScope.launch {
            getDetailCommonUseCase(
                contentId = contentId,
                contentTypeId = Config.CONTENT_TYPE_ID.STAY
            )
        }
    }

    fun requestDetailImage(contentId: String) {
        viewModelScope.launch {
            getDetailImageUseCase(contentId = contentId)
        }
    }

    fun requestDetailInfo(contentId: String) {
        viewModelScope.launch {
            getDetailInfoUseCase(
                contentId = contentId,
                contentTypeId = Config.CONTENT_TYPE_ID.STAY
            )
        }
    }

    @AssistedFactory
    interface StayViewModelFactory {
        fun create(contentId: String): StayViewModel
    }

    companion object {
        fun provideStayViewModelFactory(
            factory: StayViewModelFactory,
            contentId: String
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(contentId) as T
                }
            }
        }
    }
}