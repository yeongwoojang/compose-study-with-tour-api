package com.example.tourmanage.usecase.domain.festival

import com.example.tourmanage.common.data.server.item.FestivalItem
import kotlinx.coroutines.flow.Flow

interface GetFestivalUseCase {
    suspend operator fun invoke(areaCode: String = "", startDate: String = ""): Result<Flow<ArrayList<FestivalItem>>>
}