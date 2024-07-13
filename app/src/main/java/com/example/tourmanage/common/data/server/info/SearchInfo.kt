package com.example.tourmanage.common.data.server.info

import com.example.tourmanage.common.data.server.item.SearchItem
import com.squareup.moshi.Json

data class SearchInfo(
    @Json(name ="items") val response: List<SearchItem>

)
