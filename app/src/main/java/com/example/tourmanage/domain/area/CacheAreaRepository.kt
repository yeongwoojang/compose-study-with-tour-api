package com.example.tourmanage.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem
import kotlinx.coroutines.flow.Flow

interface CacheAreaRepository {
    suspend fun cacheArea(areaItem: AreaItem?, isSigungu: Boolean): Result<Boolean>

    suspend fun getCacheArea(isSub: Boolean): Result<Flow<AreaItem?>>

    suspend fun removeCacheArea(isSub: Boolean): Result<Boolean>
}