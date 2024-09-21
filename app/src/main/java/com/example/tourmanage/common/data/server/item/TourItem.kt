package com.example.tourmanage.common.data.server.item

import com.squareup.moshi.Json

data class TourItem(
    @Json(name = "addr1") val addr1: String?,
    @Json(name = "addr2") val addr2: String?,
    @Json(name = "areacode") val areacode: String?,
    @Json(name = "booktour") val booktour: String?,
    @Json(name = "cat1") val cat1: String?,
    @Json(name = "cat2") val cat2: String?,
    @Json(name = "cat3") val cat3: String?,
    @Json(name = "contentid") val contentid: String?,
    @Json(name = "contenttypeid") val contenttypeid: String?,
    @Json(name = "createdtime") val createdtime: String?,
    @Json(name = "firstimage") val firstimage: String?,
    @Json(name = "firstimage2") val firstimage2: String?,
    @Json(name = "cpyrhtDivCd") val cpyrhtDivCd: String?,
    @Json(name = "mapx") val mapx: String?,
    @Json(name = "mapy") val mapy: String?,
    @Json(name = "mlevel") val mlevel: String?,
    @Json(name = "modifiedtime") val modifiedtime: String?,
    @Json(name = "sigungucode") val sigungucode: String?,
    @Json(name = "tel") val tel: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "zipcode") val zipcode: String?,
    @Json(name = "showflag") val showflag: String?
): CommonBodyItem()
