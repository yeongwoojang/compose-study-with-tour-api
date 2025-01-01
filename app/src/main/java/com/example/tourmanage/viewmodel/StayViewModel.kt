package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.info.DetailCommonInfo
import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.data.server.item.DetailIntroItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.usecase.data.common.GetDetailCommonUseCase
import com.example.tourmanage.usecase.data.common.GetDetailImageUseCase
import com.example.tourmanage.usecase.data.common.GetDetailInfoUseCase
import com.example.tourmanage.usecase.domain.common.GetDetailIntroUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val _stayDataFlow = MutableStateFlow<UiState<StayPageItem>>(UiState.Loading())
    val stayDataFlow = _stayDataFlow.asStateFlow()

    private val _exceptionFlow = MutableSharedFlow<Throwable>()
    val exceptionFlow = _exceptionFlow.asSharedFlow()

    val ceh = CoroutineExceptionHandler { _, throwable ->
        Timber.e("CoroutineExceptionHandler: $throwable")
        viewModelScope.launch {
            _exceptionFlow.emit(throwable)
        }

    }
    init {
        fetchData()
    }


    private fun fetchData() {
        viewModelScope.launch(ceh) {
            _stayDataFlow.value = UiState.Loading()
            val detailImage = async { getDetailImageUseCase(contentId = contentId).getOrNull() }
            val detailIntro = async { getDetailIntroUseCase(contentId = contentId, contentTypeId = Config.CONTENT_TYPE_ID.STAY).getOrNull() }
            val detailCommonInfo = async { getDetailCommonUseCase(contentId = contentId, contentTypeId = Config.CONTENT_TYPE_ID.STAY).getOrNull() }
            val detailInfo = async { getDetailInfoUseCase(contentId = contentId, contentTypeId = Config.CONTENT_TYPE_ID.STAY).getOrThrow() }

            _stayDataFlow.value = UiState.Success(

                StayPageItem().apply {
                    images = detailImage.await() ?: ArrayList(emptyList())
                    intro = detailIntro.await() ?: emptyList()
                    common = detailCommonInfo.await()
                    info = detailInfo.await() ?: ArrayList(emptyList())
                }
            )
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

    data class StayPageItem(
        var images: ArrayList<DetailImageItem> = ArrayList(emptyList()),
        var intro: List<DetailIntroItem> = emptyList(),
        var common: DetailCommonItem? = null,
        var info: ArrayList<DetailItem> = ArrayList(emptyList())
    )
}