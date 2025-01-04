package com.example.tourmanage.domain.area

import javax.inject.Inject

class RemoveCacheAreaUseCase @Inject constructor(
    private val repository: CacheAreaRepository
) {
    suspend operator fun invoke(isSub: Boolean): Result<Boolean> {
        return repository.removeCacheArea(isSub)
    }
}