package com.example.tourmanage.domain.favor

import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.value.Config

interface GetFavorUseCase {
    suspend operator fun invoke(contentTypeId: Config.CONTENT_TYPE_ID): Result<List<FavorEntity>>
}