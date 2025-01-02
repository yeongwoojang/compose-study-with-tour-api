package com.example.tourmanage.domain.favor

import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.value.Config

interface FavorRepository {
    suspend fun addFavor(contentTypeId: String?, contentId: String?, title: String?, image: String?): Result<Boolean>
    suspend fun delFavor(contentId: String): Result<Unit>
    suspend fun getAllFavor(): Result<List<FavorEntity>>
    suspend fun getFavorByContentTypeId(contentTypeId: Config.CONTENT_TYPE_ID): Result<List<FavorEntity>>
    suspend fun isFavor(contentId: String): Result<Boolean>
}