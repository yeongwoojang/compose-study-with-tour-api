package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.squareup.moshi.Json

data class StayDetailInfo (
    @Json(name = "response") val response: CommonResponse<StayDetailItem>?
)