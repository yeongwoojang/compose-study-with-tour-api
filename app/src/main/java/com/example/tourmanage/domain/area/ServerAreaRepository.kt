package com.example.tourmanage.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem

interface ServerAreaRepository {
    suspend fun getAreaData(areaCode: String? = ""): Result<ArrayList<AreaItem>>
}