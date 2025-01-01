package com.example.tourmanage.data.favor

import com.example.tourmanage.common.data.room.FavorDao
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.domain.favor.FavorRepository
import com.example.tourmanage.error.area.TourMangeException
import timber.log.Timber
import javax.inject.Inject

class FavorRepositoryImpl @Inject constructor(
    private val dao: FavorDao
): FavorRepository {
    override suspend fun addFavor(
        contentTypeId: String?,
        contentId: String?,
        title: String?,
        image: String?
    ): Result<Boolean> = runCatching {
        if (contentTypeId.isNullOrEmpty() || contentId.isNullOrEmpty() || title.isNullOrEmpty() || image.isNullOrEmpty()) {
            throw TourMangeException.AddFavorException("DB 저장 실패")
        } else {
            val result = dao.insert(
                FavorEntity(
                contentTypeId = contentTypeId,
                contentId =contentId,
                title = title ?: "",
                image = image ?: "",
            )
            )
            result.toInt() != -1
        }
    }.onFailure {
        Result.success(false)
    }

    override suspend fun delFavor(contentId: String): Result<Unit> = runCatching{
        dao.deleteFavorItem(contentId)
    }

    override suspend fun getAllFavor(): Result<List<FavorEntity>>  = runCatching{
        val data = dao.getFavorAll()
        data.ifEmpty {
            throw TourMangeException.GetFavorException("데이터가 없습니다.")
        }
    }.onFailure {
        throw TourMangeException.GetFavorException(it.message.orEmpty())
    }

    override suspend fun getFavorByContentTypeId(contentTypeId: Config.CONTENT_TYPE_ID): Result<List<FavorEntity>>  = runCatching {
        val result = dao.getFavorByContentTypeId(
            contentTypeId.value
        )
        result.ifEmpty {
            emptyList()
        }
    }.onFailure {
        throw TourMangeException.GetFavorException(it.message.orEmpty())
    }

    override suspend fun isFavor(contentId: String): Result<Boolean> = runCatching {
        val favorEntity = dao.isFavor(contentId)
        favorEntity != null
    }.onFailure {
        throw TourMangeException.GetFavorException(it.message.orEmpty())
    }
}