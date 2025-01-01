package com.example.tourmanage.domain.favor

import javax.inject.Inject


class AddFavorUseCase @Inject constructor(
    private val repository: FavorRepository
) {
    suspend operator fun invoke(contentTypeId: String?, contentId: String?, title: String?, image: String?): Result<Boolean> {
        return repository.addFavor(contentTypeId, contentId, title, image)
    }
}