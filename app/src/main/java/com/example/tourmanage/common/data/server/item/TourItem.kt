package com.example.tourmanage.common.data.server.item

import com.squareup.moshi.Json

data class TourItem(
    @Json(name = "addr1") val addr1: String?,
    @Json(name = "areacode") val areaCode: String?,
    @Json(name = "contentid") val contentId: String?,
    @Json(name = "contenttypeid")val contentTypeId: String?,
    @Json(name = "createdtime") val createdTime: String?,
    @Json(name = "firstimage") val fullImageUrl: String?,
    @Json(name = "firstimage2") val smallImageUrl: String?,
    @Json(name = "mapx") val mapX: String?,
    @Json(name = "mapy") val mapY: String?,
    @Json(name = "tel") val tel: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "sigungucode") val sigunCode: String?,
): CommonBodyItem()
