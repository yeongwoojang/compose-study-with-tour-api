package com.example.tourmanage.common.data.server.item

import com.squareup.moshi.Json

data class SearchItem(
    @Json(name = "mapx") val mapx: String,
    @Json(name = "mapy") val mapy: String,
)
