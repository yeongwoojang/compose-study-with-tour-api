package com.example.tourmanage.common.data.server.item

import com.squareup.moshi.Json

data class DetailIntroItem(
    @Json(name = "contentid") val contentid: String?,
    @Json(name = "contenttypeid") val contenttypeid: String?,
    @Json(name = "goodstay") val goodstay: String?,
    @Json(name = "benikia") val benikia: String?,
    @Json(name = "hanok") val hanok: String?,
    @Json(name = "roomcount") val roomcount: String?,
    @Json(name = "roomtype") val roomtype: String?,
    @Json(name = "refundregulation") val refundregulation: String?,
    @Json(name = "checkintime") val checkintime: String?,
    @Json(name = "checkouttime") val checkouttime: String?,
    @Json(name = "chkcooking") val chkcooking: String?,
    @Json(name = "seminar") val seminar: String?,
    @Json(name = "sports") val sports: String?,
    @Json(name = "sauna") val sauna: String?,
    @Json(name = "beauty") val beauty: String?,
    @Json(name = "beverage") val beverage: String?,
    @Json(name = "karaoke") val karaoke: String?,
    @Json(name = "barbecue") val barbecue: String?,
    @Json(name = "campfire") val campfire: String?,
    @Json(name = "bicycle") val bicycle: String?,
    @Json(name = "fitness") val fitness: String?,
    @Json(name = "publicpc") val publicpc: String?,
    @Json(name = "publicbath") val publicbath: String?,
    @Json(name = "subfacility") val subfacility: String?,
    @Json(name = "foodplace") val foodplace: String?,
    @Json(name = "reservationurl") val reservationurl: String?,
    @Json(name = "Function…") val pickup: String?,
    @Json(name = "infocenterlodging") val infocenterlodging: String?,
    @Json(name = "parkinglodging") val parkinglodging: String?,
    @Json(name = "reservationlodging") val reservationlodging: String?,
    @Json(name = "scalelodging") val scalelodging: String?,
    @Json(name = "accomcountlodging") val accomcountlodging: String?
): CommonBodyItem()
