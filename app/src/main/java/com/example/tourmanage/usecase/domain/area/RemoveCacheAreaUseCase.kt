package com.example.tourmanage.usecase.domain.area

interface RemoveCacheAreaUseCase {
    suspend operator fun invoke(isSub: Boolean): Result<Boolean>
}