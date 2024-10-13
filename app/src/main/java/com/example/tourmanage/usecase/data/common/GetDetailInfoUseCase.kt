package com.example.tourmanage.usecase.data.common

import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.value.Config

interface GetDetailInfoUseCase {
    suspend operator fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID,
    ): Result<ArrayList<DetailItem>>
}