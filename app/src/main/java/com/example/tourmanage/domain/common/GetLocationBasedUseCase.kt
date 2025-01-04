package com.example.tourmanage.domain.common

import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.value.Config
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationBasedUseCase @Inject constructor(
    private val repository: DetailRepository
){
    suspend operator fun invoke(contentTypeId: Config.CONTENT_TYPE_ID, mapX: String, mapY: String, radius: String? = "5000"): Result<Flow<ArrayList<LocationBasedItem>>> {
        return repository.getLocationBased(contentTypeId, mapX, mapY, radius)
    }
}