package com.example.tourmanage.viewmodel

import android.content.Context
import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.setDefaultCollect
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
): ViewModel() {

    private val _myAreaTourCourse = MutableStateFlow<UiState<ArrayList<AreaBasedItem>>>(UiState.Ready())
    val myAreaTourCourse = _myAreaTourCourse

    private val _allAreaTourCourse = MutableStateFlow<UiState<ArrayList<AreaBasedItem>>>(UiState.Ready())
    val allAreaTourCourse = _allAreaTourCourse
    fun requestCourse(parentArea: AreaItem? = null, childArea: AreaItem? = null) {
        viewModelScope.launch {
            serverRepo.requestAreaBasedList(parentArea?.code, childArea?.code, Config.CONTENT_TYPE_ID.TOUR_COURSE)
                .setDefaultCollect(
                    if (parentArea == null && childArea == null) {
                        _allAreaTourCourse
                    } else {
                        _myAreaTourCourse
                    }
                )
        }
    }
}