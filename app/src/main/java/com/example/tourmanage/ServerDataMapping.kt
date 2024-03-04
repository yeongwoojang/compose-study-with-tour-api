package com.example.tourmanage

import com.example.tourmanage.common.data.StayInfo
import com.example.tourmanage.data.StayItem

fun StayInfo.toStayInfoList(): ArrayList<StayItem> {
    val result = ArrayList<StayItem>()
    response?.body?.items?.item?.forEach {
        result.add(StayItem().apply {
            this.addr1 = it.addr1
            this.areaCode = it.areaCode
            this.contentId = it.contentId
            this.contentTypeId = it.contentTypeId
            this.createdTime = it.createdTime
            this.fullImageUrl = it.fullImageUrl
            this.smallImageUrl = it.smallImageUrl
            this.mapX = it.mapX
            this.mapY = it.mapY
            this.tel = it.tel
            this.title = it.title
            this.sigunCode = it.sigunCode
        })
    }
    return result
}