package com.example.tourmanage.domain.favor

import com.example.tourmanage.common.data.room.FavorEntity
import javax.inject.Inject

class GetAllFavorUseCase @Inject constructor(
    private val repository: FavorRepository
) {
    suspend operator fun invoke(): Result<List<FavorEntity>> {
        return repository.getAllFavor()
    }
}