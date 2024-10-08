package com.example.tourmanage.usecase.domain.festival

import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow

interface GetFestivalUseCase {
    suspend operator fun invoke(areaCode: String = "", startDate: String = ""): Result<Flow<List<PosterItem>>>
}