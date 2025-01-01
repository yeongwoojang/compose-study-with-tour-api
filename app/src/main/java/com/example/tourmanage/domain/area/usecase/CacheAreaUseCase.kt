package com.example.tourmanage.domain.area.usecase

import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.domain.area.repository.CacheAreaRepository
import javax.inject.Inject

class CacheAreaUseCase @Inject constructor(
    private val repository: CacheAreaRepository
) {
    suspend operator fun invoke(areaItem: AreaItem?, isSigungu: Boolean): Result<Boolean> {
        return repository.cacheArea(areaItem, isSigungu)
    }
}