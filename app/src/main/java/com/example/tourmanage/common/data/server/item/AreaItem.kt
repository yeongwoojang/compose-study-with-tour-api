package com.example.tourmanage.common.data.server.item

import com.google.gson.annotations.Expose
import com.squareup.moshi.Json

data class AreaItem(
    @Json(name = "rnum") val rNum: String?,
    @Json(name = "code") val code: String?,
    @Json(name = "name") val name: String?,
): CommonBodyItem()