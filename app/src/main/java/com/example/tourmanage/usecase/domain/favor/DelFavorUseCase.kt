package com.example.tourmanage.usecase.domain.favor

interface DelFavorUseCase {
    suspend operator fun invoke(contentId: String): Result<Unit>
}