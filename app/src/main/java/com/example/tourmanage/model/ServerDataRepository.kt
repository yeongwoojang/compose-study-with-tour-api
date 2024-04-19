package com.example.tourmanage.model

import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.*
import com.example.tourmanage.common.value.Config
import kotlinx.coroutines.flow.Flow

interface ServerDataRepository {
    fun requestStayInfo(areaCode: String? = ""): Flow<UiState<ArrayList<StayItem>>>
    fun requestAreaCode(): Flow<UiState<ArrayList<AreaItem>>>

    fun requestStayDetailInfo(contentId: String, contentType: String): Flow<UiState<StayDetailItem>>
    fun requestOptionInfo(contentId: String, contentType: String): Flow<UiState<ArrayList<DetailItem>>>

    fun requestFestivalInfo(areaCode: String? = "", eventStartDate: String? = "", arrange: Config.ARRANGE_TYPE): Flow<UiState<ArrayList<FestivalItem>>>
}