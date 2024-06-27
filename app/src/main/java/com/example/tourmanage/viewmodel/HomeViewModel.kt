package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
): ViewModel() {

    private val _festivalItem = MutableStateFlow<UiState<ArrayList<FestivalItem>>>(UiState.Ready())
    val festivalItem = _festivalItem

    fun requestFestivalInfo(areaCode: String? = "", eventStartDate: String? = "", arrangeType: Config.ARRANGE_TYPE) {
        Timber.i("requestFestivalInfo() | areaCode: $areaCode | eventStartDate: $eventStartDate | arrangeType: $arrangeType")
        viewModelScope.launch {
            serverRepo.requestFestivalInfo(areaCode, eventStartDate, arrangeType)
                .onStart { _festivalItem.value = UiState.Loading() }
                .catch { _festivalItem.value = UiState.Error(it.message!!) }
                .collect { _festivalItem.value = it }

        }

    }
}