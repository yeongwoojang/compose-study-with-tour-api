package com.example.tourmanage.common.data.server.common

import com.example.tourmanage.common.data.ResponseBodyList
import com.example.tourmanage.common.data.server.item.CommonBodyItem
import com.squareup.moshi.Json

data class CommonResponse<T: CommonBodyItem>(
    @Json(name = "header") val header: ResponseHeader?,
    @Json(name = "body") val body: ResponseBodyList<T>?
)
