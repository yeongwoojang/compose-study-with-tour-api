package com.example.tourmanage.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.data.server.item.DetailIntroItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.common.GetDetailCommonUseCase
import com.example.tourmanage.data.common.GetDetailImageUseCase
import com.example.tourmanage.data.common.GetDetailInfoUseCase
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.domain.common.GetDetailIntroUseCase
import com.example.tourmanage.domain.favor.AddFavorUseCase
import com.example.tourmanage.domain.favor.DelFavorUseCase
import com.example.tourmanage.domain.favor.GetFavorUseCase
import com.example.tourmanage.domain.favor.IsFavorUseCase
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
    private val addFavorUseCase: AddFavorUseCase,
    private val delFavorUseCase: DelFavorUseCase,
    private val getFavorUseCase: GetFavorUseCase,
    private val isFavorUseCase: IsFavorUseCase,

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

    private val _toggleFavorFlow = MutableStateFlow<UiState<Boolean>>(UiState.Loading())
    val toggleFavorFlow = _toggleFavorFlow.asStateFlow()

    private val _isFavor = MutableSharedFlow<Boolean>(replay = 1)
    val isFavor = _isFavor.asSharedFlow()

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

            val isFavor = async { isFavorUseCase(contentId).getOrElse { false } }
            _isFavor.emit(isFavor.await())
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

    fun requestDelFavor(posterItem: PosterItem) {
        viewModelScope.launch {
            delFavorUseCase(posterItem.contentId)
        }
    }

    fun requestToggleFavor(posterItem: PosterItem) {
        viewModelScope.launch(ceh) {
            val isSuccess = addFavorUseCase(
                Config.CONTENT_TYPE_ID.STAY.value,
                posterItem.contentId,
                posterItem.title,
                posterItem.imgUrl
            ).getOrElse { false }
            _toggleFavorFlow.value = UiState.Success(isSuccess)
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
        var info: ArrayList<DetailItem> = ArrayList(emptyList()),
        var isFavor: Boolean = false,
    )
}