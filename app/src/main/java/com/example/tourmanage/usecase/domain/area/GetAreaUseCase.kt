package com.example.tourmanage.usecase.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem
import kotlinx.coroutines.flow.Flow

interface GetAreaUseCase {
    suspend operator fun invoke(
        areaCode: String? = ""
    ): Result<Flow<ArrayList<AreaItem>>>
}