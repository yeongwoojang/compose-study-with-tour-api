package com.example.tourmanage.model

import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.data.server.item.TourItem
import kotlinx.coroutines.flow.Flow

interface ServerDataRepository {
    fun requestStayInfo(areaCode: String? = ""): Flow<UiState<ArrayList<StayItem>>>
    fun requestAreaCode(): Flow<UiState<ArrayList<AreaItem>>>

    fun requestStayDetailInfo(contentId: String, contentType: String): Flow<UiState<StayDetailItem>>
    fun requestOptionInfo(contentId: String, contentType: String): Flow<UiState<ArrayList<DetailItem>>>
    fun requestTourInfo(areaCode: String? = ""): Flow<UiState<ArrayList<TourItem>>>
}