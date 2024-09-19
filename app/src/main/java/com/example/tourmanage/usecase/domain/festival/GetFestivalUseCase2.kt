package com.example.tourmanage.usecase.domain.festival

import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow

interface GetFestivalUseCase2 {
    suspend operator fun invoke(areaCode: String = "", startDate: String = ""): Result<List<PosterItem>>
}