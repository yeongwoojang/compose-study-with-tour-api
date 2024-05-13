package com.example.tourmanage.common

import com.example.tourmanage.common.data.server.item.AreaItem

sealed class CachedAreaEvent {
    object Error : CachedAreaEvent()
    class setCachedAreaEvnet(
        val areaItem: AreaItem
    ): CachedAreaEvent()
}