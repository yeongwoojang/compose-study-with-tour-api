package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.data.server.item.SearchItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.usecase.domain.common.GetLocationBasedUseCase
import com.example.tourmanage.usecase.domain.search.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AreaViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val getLocationBasedUseCase: GetLocationBasedUseCase

): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i("${throwable.message}")
    }


    private val queryFlow = MutableSharedFlow<String>()

    private val _currentMenu = MutableSharedFlow<Config.CONTENT_TYPE_ID>(replay = 1)
    val currentMenu = _currentMenu.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val locationFlow = queryFlow.flatMapLatest { query ->
        flow {
            getSearchUseCase(query).getOrThrow().collect {
                emit(it)
            }
        }
    }.shareIn(viewModelScope + exceptionHandler, SharingStarted.Eagerly)



    init {
        setMenu(Config.CONTENT_TYPE_ID.FESTIVAL)
    }

    fun handleQuery(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }

    fun setMenu(menu: Config.CONTENT_TYPE_ID) {
        viewModelScope.launch {
            _currentMenu.emit(menu)
        }
    }

}