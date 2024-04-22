package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.setDefaultCollect
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
): ViewModel() {
    private val _myLocFestival = MutableStateFlow<UiState<ArrayList<LocationBasedItem>>>(UiState.Ready())
    val myLocFestival = _myLocFestival

    private val _areaFestival = MutableStateFlow<UiState<ArrayList<FestivalItem>>>(UiState.Ready())
    val areaFestival = _areaFestival
    fun requestMyLocationFestival() {
        viewModelScope.launch {
            val currentGPS = ServerGlobal.getCurrentGPS()
            val longitude = currentGPS.first
            val latitude = currentGPS.second
            serverRepo.requestLocationBasedList(contentTypeId = Config.CONTENT_TYPE_ID.FESTIVAL, mapX = longitude, mapY = latitude, arrange = Config.ARRANGE_TYPE.O)
                .setDefaultCollect(_myLocFestival)
        }
    }

    fun requestAreaFestival() {
        viewModelScope.launch {
            serverRepo.requestFestivalInfo(areaCode = "6", arrange = Config.ARRANGE_TYPE.O)
                .setDefaultCollect(_areaFestival)
        }
    }
}