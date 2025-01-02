package com.example.tourmanage.domain.favor

import javax.inject.Inject

class DelFavorUseCase @Inject constructor(
    private val repository: FavorRepository
) {
    suspend operator fun invoke(contentId: String): Result<Unit> {
        return repository.delFavor(contentId)
    }
}