package com.example.tourmanage.usecase.domain.stay

import com.example.tourmanage.common.data.server.item.StayItem
import kotlinx.coroutines.flow.Flow

interface GetStayUseCase {
    suspend operator fun invoke(areaCode: String?, sigunguCode: String?): Result<Flow<List<StayItem>>>
}