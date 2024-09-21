package com.example.tourmanage.usecase.domain.common

import androidx.paging.PagingData
import com.example.tourmanage.common.data.server.item.TourItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow

interface GetTourInfoUseCase {
    suspend operator fun invoke(contentTypeId: Config.CONTENT_TYPE_ID): Result<Flow<PagingData<PosterItem>>>
}