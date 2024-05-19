package com.example.tourmanage.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.setDefaultCollect
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
    ): CommonViewModel(serverRepo, dataStore) {

    private val _curAreaTourCourse = MutableStateFlow<UiState<ArrayList<AreaBasedItem>>>(UiState.Ready())
    val curAreaTourCourse = _curAreaTourCourse

    private val _allAreaTourCourse = MutableStateFlow<UiState<ArrayList<AreaBasedItem>>>(UiState.Ready())
    val allAreaTourCourse = _allAreaTourCourse

    private val _myAreaTourCourse = MutableStateFlow<UiState<ArrayList<LocationBasedItem>>>(UiState.Ready())
    val myAreaTourCourse = _myAreaTourCourse
    fun requestCourse(parentArea: AreaItem? = null, childArea: AreaItem? = null) {
        viewModelScope.launch {
            serverRepo.requestAreaBasedList(parentArea?.code, childArea?.code, Config.CONTENT_TYPE_ID.TOUR_COURSE)
                .setDefaultCollect(
                    if (parentArea == null && childArea == null) {
                        _allAreaTourCourse
                    } else {
                        curAreaTourCourse
                    }
                )
        }
    }

    override fun requestMyLocationInfo(typeId: Config.CONTENT_TYPE_ID) {
        viewModelScope.launch {
            val currentGPS = ServerGlobal.getCurrentGPS()
            val longitude = currentGPS.mapX
            val latitude = currentGPS.mapY
            serverRepo.requestLocationBasedList(contentTypeId = typeId, mapX = longitude, mapY = latitude, arrange = Config.ARRANGE_TYPE.O)
                .setDefaultCollect(_myAreaTourCourse)
        }
    }
}