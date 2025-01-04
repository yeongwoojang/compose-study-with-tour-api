package com.example.tourmanage.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCacheAreaUseCase @Inject constructor(
    private val repository: CacheAreaRepository
) {
    suspend operator fun invoke(isSub: Boolean): Result<Flow<AreaItem?>> {
        return repository.getCacheArea(isSub)
    }
}
