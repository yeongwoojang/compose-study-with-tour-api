package com.example.tourmanage.common.data.server.common

import com.example.tourmanage.common.data.server.item.CommonBodyItem
import com.squareup.moshi.Json

data class ResponseBody<T: CommonBodyItem>(
    @Json(name = "item") val item: List<T>?,
)
