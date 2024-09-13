package com.example.tourmanage.usecase.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem

interface GetAreaUseCase {
    suspend operator fun invoke(
        areaCode: String? = "",
    ): Result<ArrayList<AreaItem>>
}