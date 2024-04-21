package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.data.server.item.TourItem
import com.squareup.moshi.Json

data class TourInfo(
    @Json(name = "response") val response: CommonResponse<TourItem>?
)

