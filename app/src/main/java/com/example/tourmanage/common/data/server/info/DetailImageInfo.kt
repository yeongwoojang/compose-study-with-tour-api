package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.squareup.moshi.Json

data class DetailImageInfo(
    @Json(name = "response") val response: CommonResponse<DetailImageItem>?
)
