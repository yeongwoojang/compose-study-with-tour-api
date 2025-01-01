package com.example.tourmanage.data.common

import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.value.Config
import kotlinx.coroutines.flow.Flow

interface GetDetailCommonUseCase {
    suspend operator fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID,
    ): Result<DetailCommonItem?>
}