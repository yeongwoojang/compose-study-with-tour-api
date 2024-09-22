package com.example.tourmanage.usecase.domain.common

import androidx.paging.PagingData
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow

interface GetAreaBasedUseCase {
    suspend operator fun invoke(
        areaCode: String,
        sigunguCode: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<Flow<PagingData<PosterItem>>>
}