package com.example.tourmanage.usecase.domain.common

import com.example.tourmanage.common.data.server.item.DetailIntroItem
import com.example.tourmanage.common.value.Config

interface GetDetailIntroUseCase {
    suspend operator fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<List<DetailIntroItem>>
}