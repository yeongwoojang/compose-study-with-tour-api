package com.example.tourmanage.common.data.server.item

import com.squareup.moshi.Json

data class StayDetailItem(
    @Json(name = "contentid") val contentId: String?,
    @Json(name = "contenttypeid") val contentTypeId: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "createdtime") val createdTime: String?,
    @Json(name = "modifiedtime") val modifiedTime: String?,
    @Json(name = "tel") val tel: String?,
    @Json(name = "telname") val telName: String?,
    @Json(name = "hompage") val hompageUrl: String?,
    @Json(name = "booktour") val bookTour: String?,
    @Json(name = "firstimage") val mainImage: String?,
    @Json(name = "firstimage2") val subImage: String?,
    @Json(name = "cpyrhtDivCd") val cpyrhtDivCd: String?,
    @Json(name = "cat1") val topCode: String?,
    @Json(name = "cat2") val midCode: String?,
    @Json(name = "cat3") val bottomCode: String?,
    @Json(name = "addr1") val addr1: String?,
    @Json(name = "addr2") val addr2: String?,
    @Json(name = "zipcode") val zipCode: String?,
    @Json(name = "mapx") val mapX: String?,
    @Json(name = "mapy") val mapY: String?,
    @Json(name = "mlevel") val mLevel: String?,
    @Json(name = "overview") val overview: String?,
): CommonBodyItem()
