package com.example.tourmanage.model

import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.*
import com.example.tourmanage.common.value.Config
import kotlinx.coroutines.flow.Flow

interface ServerDataRepository {
    fun requestStayInfo(areaCode: String? = "", sigunguCode: String? = ""): Flow<UiState<ArrayList<StayItem>>>
    fun requestAreaCode(areaCode: String? = "", isInit: Boolean = false): Flow<UiState<ArrayList<AreaItem>>>
    fun requestAreaBasedList(areaCode: String? = "", sigunguCode: String? = "", contentTypeId: Config.CONTENT_TYPE_ID? = Config.CONTENT_TYPE_ID.TOUR_SPOT): Flow<UiState<ArrayList<AreaBasedItem>>>
    fun requestStayDetailInfo(contentId: String, contentType: String): Flow<UiState<DetailCommonItem>>
    fun requestOptionInfo(contentId: String, contentType: String): Flow<UiState<ArrayList<DetailItem>>>
    fun requestFestivalInfo(areaCode: String? = "", eventStartDate: String? = ""): Flow<UiState<ArrayList<FestivalItem>>>
    fun requestLocationBasedList(contentTypeId: Config.CONTENT_TYPE_ID? = Config.CONTENT_TYPE_ID.FESTIVAL, mapX: String? = "", mapY: String? = "", radius: String? = "5000", arrange: Config.ARRANGE_TYPE): Flow<UiState<ArrayList<LocationBasedItem>>>
}