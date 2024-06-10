package com.example.tourmanage.viewmodel

import android.content.Context
import android.location.Address
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.setDefaultCollect
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
    ): CommonViewModel(serverRepo, dataStore) {

    private val _tourCourseInfo = MutableStateFlow<UiState<TourCourse>>(UiState.Ready())
    val tourCourseInfo = _tourCourseInfo

    fun requestCourseInfo(typeId: Config.CONTENT_TYPE_ID, parentArea: AreaItem? = null, childArea: AreaItem? = null) {
        viewModelScope.launch {
            val currentGPS = ServerGlobal.getCurrentGPS()
            val longitude = currentGPS.mapX
            val latitude = currentGPS.mapY

            // 서버 요청을 비동기로 실행
            val allAreaTourCourse = serverRepo.requestAreaBasedList(contentTypeId = Config.CONTENT_TYPE_ID.TOUR_COURSE)
            val myAreaTourCourse = serverRepo.requestLocationBasedList(contentTypeId = typeId, mapX = longitude, mapY = latitude, arrange = Config.ARRANGE_TYPE.O)
            val curAreaTourCourse = if (parentArea != null && childArea != null) {
                serverRepo.requestAreaBasedList(parentArea.code, childArea.code, Config.CONTENT_TYPE_ID.TOUR_COURSE)
            } else {
                null
            }

            // 모든 요청이 완료될 때까지 기다림
            val tourCourse = TourCourse()

            val firstFlow = if (curAreaTourCourse != null) {
                allAreaTourCourse.combine(curAreaTourCourse) { allArea, curArea ->
                    if (allArea is UiState.Success) tourCourse.allAreaTourCourse = allArea.data!!
                    if (curArea is UiState.Success) tourCourse.curAreaTourCourse = curArea.data!!
                    tourCourse
                }
            } else {
                allAreaTourCourse.map {
                    if (it is UiState.Success) tourCourse.allAreaTourCourse = it.data!!
                    tourCourse
                }
            }

            firstFlow.combine(myAreaTourCourse) { combinedResult, myArea ->
                if (myArea is UiState.Success) combinedResult.myAreaTourCourse = myArea.data!!
                combinedResult
            }.onStart {
                _tourCourseInfo.value = UiState.Loading()
            }.collect {
                _tourCourseInfo.value = UiState.Success(it)
            }
        }
    }

    fun getCurrentAddress(context: Context) = flow<Address?> {
        val currentGps = ServerGlobal.getCurrentGPS()
        val address = viewModelScope.async {
            ServerGlobal.getAddress(context, currentGps.mapY.isEmptyString("0").toDouble(), currentGps.mapX.isEmptyString("0").toDouble())
        }

        val result = address.await()
        emit(result)
    }
}

data class TourCourse(
    var allAreaTourCourse: ArrayList<AreaBasedItem> = ArrayList(emptyList()),
    var curAreaTourCourse: ArrayList<AreaBasedItem> = ArrayList(emptyList()),
    var myAreaTourCourse: ArrayList<LocationBasedItem> = ArrayList(emptyList())
)