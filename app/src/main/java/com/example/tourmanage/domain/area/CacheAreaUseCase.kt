package com.example.tourmanage.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem
import javax.inject.Inject

class CacheAreaUseCase @Inject constructor(
    private val repository: CacheAreaRepository
) {
    suspend operator fun invoke(areaItem: AreaItem?, isSigungu: Boolean): Result<Boolean> {
        return repository.cacheArea(areaItem, isSigungu)
    }
}