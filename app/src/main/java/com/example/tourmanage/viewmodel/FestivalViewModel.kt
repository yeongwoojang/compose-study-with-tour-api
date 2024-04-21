package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.LocationBasedItem
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
class FestivalViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
): ViewModel() {
    private val _festivalItem = MutableStateFlow<UiState<ArrayList<LocationBasedItem>>>(UiState.Ready())
    val festivalItem = _festivalItem
    fun requestFestival() {
        viewModelScope.launch {
            Timber.i("TEST_LOG | ${Config.CONTENT_TYPE_ID.FESTIVAL.name} | ${Config.CONTENT_TYPE_ID.FESTIVAL.value}")
            val currentGPS = ServerGlobal.getCurrentGPS()
            val longitude = currentGPS.first
            val latitude = currentGPS.second
            serverRepo.requestLocationBasedList(contentTypeId = Config.CONTENT_TYPE_ID.FESTIVAL, mapX = longitude, mapY = latitude, arrange = Config.ARRANGE_TYPE.O)
                .onStart { _festivalItem.value = UiState.Loading() }
                .catch { _festivalItem.value = UiState.Error(it.message!!) }
                .collect { _festivalItem.value = it }
        }
    }
}