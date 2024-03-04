package com.example.tourmanage.data

import com.squareup.moshi.Json

data class StayItem(
    var addr1: String? = "",
    var areaCode: String? = "",
    var contentId: String? = "",
    var contentTypeId: String? = "",
    var createdTime: String? = "",
    var fullImageUrl: String? = "",
    var smallImageUrl: String? = "",
    var mapX: String? = "",
    var mapY: String? = "",
    var tel: String? = "",
    var title: String? = "",
    var sigunCode: String? = "",
)
