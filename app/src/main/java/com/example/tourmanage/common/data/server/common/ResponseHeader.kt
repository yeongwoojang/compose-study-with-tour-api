package com.example.tourmanage.common.data.server.common

import com.squareup.moshi.Json

data class ResponseHeader (
    @Json(name = "resultCode") val resultCode: String?,
    @Json(name = "resultMsg") val resultMsg: String?,
)