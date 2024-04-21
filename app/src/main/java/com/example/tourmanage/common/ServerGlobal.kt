package com.example.tourmanage.common

import android.location.Location
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isNotNullOrEmpty
import timber.log.Timber

object ServerGlobal {
    private val areaCodeMap = HashMap<String, String>()
    private var currentGPS = Pair<String, String>("", "")


    fun getAreaCode(code: String): String? {
        val result = areaCodeMap[code]
        Timber.i("getAreaCode() | code: $code | result: $result")
        return result
    }

    fun getAreaName(name: String): String? {
        return areaCodeMap.entries.find { it.value == name }?.key
    }

    fun isValidAreaName(name: String): Boolean {
        return areaCodeMap.entries.find { it.value == name } != null
    }

    fun setAreaCodeMap(areaCodeList: ArrayList<AreaItem>) {
        Timber.i("setAreaCodeMap() | areaCodeList: $areaCodeList")
        areaCodeList.forEach {
            if (it.code.isNotNullOrEmpty() && it.name.isNotNullOrEmpty()) {
                val code = it.code!!
                val name = it.name!!
                areaCodeMap[code] = name
            }
        }
    }

    fun setGPS(location: Location) {
        val longitude = location.longitude.toString().isEmptyString("")
        val latitude = location.latitude.toString().isEmptyString("")
        Timber.d("setGPS() | longitude: $longitude | latitude: $latitude")
        currentGPS = Pair(longitude, latitude)
    }

    fun getCurrentGPS() = currentGPS
}