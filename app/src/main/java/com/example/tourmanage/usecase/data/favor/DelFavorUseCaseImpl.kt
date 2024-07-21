package com.example.tourmanage.usecase.data.favor

import com.example.tourmanage.common.data.room.FavorDao
import com.example.tourmanage.usecase.domain.favor.DelFavorUseCase
import javax.inject.Inject

class DelFavorUseCaseImpl @Inject constructor(
    private val dao: FavorDao
): DelFavorUseCase {
    override suspend fun invoke(contentId: String): Result<Unit> = runCatching{
        dao.deleteFavorItem(contentId)
    }
}