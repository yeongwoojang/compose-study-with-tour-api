package com.example.tourmanage

import com.example.tourmanage.common.data.server.common.CommonResponse
import com.example.tourmanage.common.data.server.info.AreaInfo
import com.example.tourmanage.common.data.server.info.DetailInfo
import com.example.tourmanage.common.data.server.info.StayDetailInfo
import com.example.tourmanage.common.data.server.info.StayInfo
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.example.tourmanage.common.data.server.item.StayItem

fun StayInfo.toStayInfoList(): ArrayList<StayItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<StayItem>
    } ?: ArrayList(emptyList())
}

fun AreaInfo.toAreaInfoList(): ArrayList<AreaItem> {
    return response?.body?.items?.item?.let {
        it as ArrayList<AreaItem>
    } ?: ArrayList(emptyList())
}

fun StayDetailInfo.toStayDetail(): StayDetailItem?{
    return response?.body?.items?.item?.let {
        it[0]
    }
}

fun DetailInfo.toDetailItems(): ArrayList<DetailItem>? {
    return response?.body?.items?.item?.let {
        it as ArrayList<DetailItem>
    } ?: ArrayList(emptyList())
}