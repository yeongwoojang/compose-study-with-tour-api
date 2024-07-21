package com.example.tourmanage.usecase.domain.favor

import com.example.tourmanage.common.data.room.FavorEntity

interface GetAllFavorUseCase {
    suspend operator fun invoke(): Result<List<FavorEntity>>
}