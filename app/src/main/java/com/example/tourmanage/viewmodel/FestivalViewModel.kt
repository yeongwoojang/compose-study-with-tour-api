package com.example.tourmanage.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
): CommonViewModel(serverRepo, dataStore) {
    private val _festivalInfo = MutableStateFlow<UiState<Festival>>(UiState.Ready())
    val festivalInfo = _festivalInfo

    private val clickItemFlow = MutableSharedFlow<Any>()

    val goDetailFlow = clickItemFlow
        .flatMapLatest { item ->
            flow<DetailFestival> {
                emit(DetailFestival().apply {
                    if (item is FestivalItem) {
                        title = item.title
                    } else if (item is LocationBasedItem) {
                        title = item.title
                    }
                })
            }
        }

    fun requestFestivalInfo(typeId: Config.CONTENT_TYPE_ID) {
        val isSkip = _festivalInfo.value !is UiState.Ready
        Timber.d("requestFestivalInfo() | isSkip: $isSkip")
        if (!isSkip) {
            viewModelScope.launch {
                val recommend = serverRepo.requestFestivalInfo(areaCode = "6")

                val currentGPS = ServerGlobal.getCurrentGPS()
                val longitude = currentGPS.mapX
                val latitude = currentGPS.mapY

                val myLocal = serverRepo.requestLocationBasedList(contentTypeId = typeId, mapX = longitude, mapY = latitude, arrange = Config.ARRANGE_TYPE.O)
                recommend.combine(myLocal) { recommend, local ->
                    Festival().apply {
                        if (recommend is UiState.Success) this.recommendFestival = recommend.data!!
                        if (local is UiState.Success) this.localFestival = local.data!!
                    }

                }.onStart {
                    _festivalInfo.value = UiState.Loading()
                }.collect {
                    _festivalInfo.value = UiState.Success(it)
                }
            }
        }
    }

    fun choiceFestival(item: Any) {
        viewModelScope.launch {
            clickItemFlow.emit(item)
        }
    }
}

data class Festival(
    var recommendFestival: ArrayList<FestivalItem> = ArrayList(emptyList()),
    var localFestival: ArrayList<LocationBasedItem> = ArrayList(emptyList())
)

data class DetailFestival(
    var title: String? = "",
    var contentId: String? = "",
    var addr1: String? = "",
    var addr2: String? = "",
    var contentTypeId: String? = "",
    var mainImage: String? = ""
)