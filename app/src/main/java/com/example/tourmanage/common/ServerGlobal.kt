package com.example.tourmanage.common

import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isNotNullOrEmpty
import timber.log.Timber

object ServerGlobal {
    private val areaCodeMap = HashMap<String, String>()

    fun getAreaCode(code: String): String? {
        val result = areaCodeMap[code]
        Timber.i("getAreaCode() | code: $code | result: $result")
        return result
    }

    fun getAreaName(name: String): String? {
        return areaCodeMap.entries.find { it.value == name }?.key
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

}