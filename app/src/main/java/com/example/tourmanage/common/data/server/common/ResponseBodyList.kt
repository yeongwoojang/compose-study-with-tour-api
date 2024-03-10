package com.example.tourmanage.common.data

import com.example.tourmanage.common.data.server.item.CommonBodyItem
import com.example.tourmanage.common.data.server.common.ResponseBody
import com.squareup.moshi.Json

data class ResponseBodyList<T: CommonBodyItem>(
    @Json(name = "items") val items: ResponseBody<T>?
)