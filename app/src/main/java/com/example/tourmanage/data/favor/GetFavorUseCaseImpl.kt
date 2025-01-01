package com.example.tourmanage.data.favor

import com.example.tourmanage.common.data.room.FavorDao
import com.example.tourmanage.common.data.room.FavorDatabase
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.domain.favor.GetFavorUseCase
import com.example.tourmanage.error.area.TourMangeException
import javax.inject.Inject

class GetFavorUseCaseImpl @Inject constructor(
    private val dao: FavorDao
): GetFavorUseCase {
    override suspend fun invoke(contentTypeId: Config.CONTENT_TYPE_ID): Result<List<FavorEntity>> = runCatching {
        val result = dao.getFavorByContentTypeId(
            contentTypeId.value
        )
        result.ifEmpty {
            emptyList()
        }
    }.onFailure {
        throw TourMangeException.GetFavorException(it.message.orEmpty())

    }
}