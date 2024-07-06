package com.example.tourmanage.usecase.data.common

import com.example.tourmanage.common.data.server.info.DetailInfo
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.value.Config
import kotlinx.coroutines.flow.Flow

interface GetDetailInfoUseCase {
    suspend operator fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID,
    ): Result<Flow<ArrayList<DetailItem>>>
}