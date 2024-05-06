package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.squareup.moshi.Json

data class AreaBasedInfo(
    @Json(name = "response") val response: CommonResponse<AreaBasedItem>?
)

