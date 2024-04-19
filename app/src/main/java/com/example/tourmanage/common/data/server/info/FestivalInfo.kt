package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.squareup.moshi.Json

data class FestivalInfo(
    @Json(name = "response") val response: CommonResponse<FestivalItem>?
)