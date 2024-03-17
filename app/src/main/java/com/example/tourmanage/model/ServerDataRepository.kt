package com.example.tourmanage.model

import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.StayItem
import kotlinx.coroutines.flow.Flow

interface ServerDataRepository {
    fun requestStayInfo(areaCode: String? = ""): Flow<UiState<ArrayList<StayItem>>>
    fun requestAreaCode(): Flow<UiState<ArrayList<AreaItem>>>
}