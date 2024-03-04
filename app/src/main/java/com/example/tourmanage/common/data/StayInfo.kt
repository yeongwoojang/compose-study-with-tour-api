package com.example.tourmanage.common.data

import com.squareup.moshi.Json

data class StayInfo(
    @Json(name = "response") val response: ResponseItem?
)

data class ResponseItem(
    @Json(name = "header") val header: HeaderItem?,
    @Json(name = "body") val body: Body?
)

data class Body(
    @Json(name = "items") val items: BodyList?
)

data class BodyList (
    @Json(name = "item") val item: List<BodyItem>?
)

data class
BodyItem(
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
)