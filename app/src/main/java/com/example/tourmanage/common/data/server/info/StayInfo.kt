package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.StayItem
import com.squareup.moshi.Json

data class StayInfo(
    @Json(name = "response") val response: CommonResponse<StayItem>?
)

