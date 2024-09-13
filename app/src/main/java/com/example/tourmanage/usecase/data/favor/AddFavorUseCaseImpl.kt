package com.example.tourmanage.usecase.data.favor

import com.example.tourmanage.common.data.room.FavorDao
import com.example.tourmanage.common.data.room.FavorDatabase
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.usecase.domain.favor.AddFavorUseCase
import javax.inject.Inject

class AddFavorUseCaseImpl @Inject constructor(
    private val dao: FavorDao
): AddFavorUseCase {
    override suspend fun invoke(
        contentTypeId: String?,
        contentId: String?,
        title: String?,
        image: String?
    ): Result<Long> = runCatching {
        if (contentTypeId.isNullOrEmpty() || contentId.isNullOrEmpty() || title.isNullOrEmpty() || image.isNullOrEmpty()) {
            throw TourMangeException.AddFavorException("DB 저장 실패")
        } else {
            dao.insert(FavorEntity(
                contentTypeId = contentTypeId,
                contentId =contentId,
                title = title,
                image = image,
            ))
        }
    }.onFailure {
        throw TourMangeException.AddFavorException(it.message.orEmpty())
    }
}