package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.squareup.moshi.Json

data class DetailInfo(
    @Json(name = "response") val response: CommonResponse<DetailItem>?
)
