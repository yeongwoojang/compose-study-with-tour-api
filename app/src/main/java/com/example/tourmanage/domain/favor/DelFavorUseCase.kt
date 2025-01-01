package com.example.tourmanage.domain.favor

interface DelFavorUseCase {
    suspend operator fun invoke(contentId: String): Result<Unit>
}