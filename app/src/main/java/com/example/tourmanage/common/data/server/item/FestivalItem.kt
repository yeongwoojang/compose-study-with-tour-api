package com.example.tourmanage.common.data.server.item

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class FestivalItem(
    @Json(name = "addr1") val addr1: String?,
    @Json(name = "addr2") val addr2: String?,
    @Json(name = "areacode") val areaCode: String?,
    @Json(name = "booktour") val booktour: String?,
    @Json(name = "cat1") val cat1: String?,
    @Json(name = "cat2") val cat2: String?,
    @Json(name = "cat3") val cat3: String?,
    @Json(name = "contentid") val contentId: String?,
    @Json(name = "contenttypeid") val contentTypeId: String?,
    @Json(name = "createdtime") val createdTime: String?,
    @Json(name = "eventstartdate") val eventStateDate: String?,
    @Json(name = "eventenddate") val eventEndDate: String?,
    @Json(name = "firstimage") val mainImage: String?,
    @Json(name = "firstimage2") val thumbImage: String?,
    @Json(name = "cpythDivCd") val cpythDivCd: String?,
    @Json(name = "mapx") val mapX: String?,
    @Json(name = "mapy") val mapY: String?,
    @Json(name = "mlevel") val mapLebel: String?,
    @Json(name = "modifiedtime") val modifiedTime: String?,
    @Json(name = "sigungucode") val sigunguCode: String?,
    @Json(name = "tel") val tel: String?,
    @Json(name = "title") val title: String?,
): CommonBodyItem(), Parcelable