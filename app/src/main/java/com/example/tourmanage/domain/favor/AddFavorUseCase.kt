package com.example.tourmanage.domain.favor

interface AddFavorUseCase {
    suspend operator fun invoke(contentTypeId: String?, contentId: String?, title: String?, image: String?): Result<Long>
}