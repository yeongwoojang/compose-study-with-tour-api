package com.example.tourmanage.domain.area.usecase

import com.example.tourmanage.domain.area.repository.CacheAreaRepository
import javax.inject.Inject

class RemoveCacheAreaUseCase @Inject constructor(
    private val repository: CacheAreaRepository
) {
    suspend operator fun invoke(isSub: Boolean): Result<Boolean> {
        return repository.removeCacheArea(isSub)
    }
}