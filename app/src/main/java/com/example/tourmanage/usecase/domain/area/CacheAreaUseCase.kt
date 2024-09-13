package com.example.tourmanage.usecase.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem

interface CacheAreaUseCase {
    suspend operator fun invoke(areaItem: AreaItem?, isSigungu: Boolean): Result<Boolean>
}