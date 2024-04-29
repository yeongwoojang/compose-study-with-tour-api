package com.example.tourmanage.common

import android.location.Location
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import timber.log.Timber

object ServerGlobal {
    private var currentGPS = Pair<String, String>("", "")
    private val parentAreaList = ArrayList<AreaItem>()

    fun setAreaCodeList(areaCodeList: ArrayList<AreaItem>) {
        parentAreaList.addAll(areaCodeList)
    }

    fun getParentAreaList(): ArrayList<AreaItem> = parentAreaList

    fun getAreaCode(areaName: String) = parentAreaList.find { it.name == areaName }?.code

    fun setGPS(location: Location) {
        val longitude = location.longitude.toString().isEmptyString("")
        val latitude = location.latitude.toString().isEmptyString("")
        Timber.d("setGPS() | longitude: $longitude | latitude: $latitude")
        currentGPS = Pair(longitude, latitude)
    }

    fun getCurrentGPS() = currentGPS
}