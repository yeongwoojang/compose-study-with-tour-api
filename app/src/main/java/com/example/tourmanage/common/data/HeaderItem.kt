package com.example.tourmanage.common.data

import com.squareup.moshi.Json

data class HeaderItem (
    @Json(name = "resultCode") val resultCode: String?,
    @Json(name = "resultMsg") val resultMsg: String?,
)