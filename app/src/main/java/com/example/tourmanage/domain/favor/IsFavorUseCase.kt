package com.example.tourmanage.domain.favor

import javax.inject.Inject

class IsFavorUseCase @Inject constructor(
    private val repository: FavorRepository
) {
    suspend operator fun invoke(contentId: String): Result<Boolean> {
        return repository.isFavor(contentId)
    }
}