package com.example.tourmanage.usecase.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem
import kotlinx.coroutines.flow.Flow

interface GetCacheAreaUseCase {
    suspend operator fun invoke(isSub: Boolean): Result<Flow<AreaItem?>>
}