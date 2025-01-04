package com.example.tourmanage.domain.festival

import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow

interface FestivalRepository {
    suspend fun getFestival(areaCode: String = "", startDate: String = ""): Result<Flow<List<PosterItem>>>

}