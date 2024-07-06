package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.squareup.moshi.Json

data class DetailCommonInfo (
    @Json(name = "response") val response: CommonResponse<DetailCommonItem>?
)