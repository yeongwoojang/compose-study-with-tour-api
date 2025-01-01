package com.example.tourmanage.data.favor

import com.example.tourmanage.common.data.room.FavorDao
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.domain.favor.GetAllFavorUseCase
import com.example.tourmanage.error.area.TourMangeException
import javax.inject.Inject

class GetAllFavorUseCaseImpl @Inject constructor(
    private val dao: FavorDao
): GetAllFavorUseCase {
    override suspend fun invoke(): Result<List<FavorEntity>> = runCatching{
        val data = dao.getFavorAll()
        data.ifEmpty {
            throw TourMangeException.GetFavorException("데이터가 없습니다.")
        }
    }.onFailure {
        throw TourMangeException.GetFavorException(it.message.orEmpty())
    }
}