package com.example.tourmanage.domain.area.usecase

import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.domain.area.repository.CacheAreaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCacheAreaUseCase @Inject constructor(
    private val repository: CacheAreaRepository
) {
    suspend operator fun invoke(isSub: Boolean): Result<Flow<AreaItem?>> {
        return repository.getCacheArea(isSub)
    }
}
