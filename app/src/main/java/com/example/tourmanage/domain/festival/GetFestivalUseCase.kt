package com.example.tourmanage.domain.festival

import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFestivalUseCase @Inject constructor(
    private val repository: FestivalRepository
){
    suspend operator fun invoke(areaCode: String = "", startDate: String = ""): Result<Flow<List<PosterItem>>> {
        return repository.getFestival(areaCode, startDate)
    }
}