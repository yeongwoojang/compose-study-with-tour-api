package com.example.tourmanage.domain.area

import com.example.tourmanage.common.data.server.item.AreaItem
import javax.inject.Inject

class GetAreaUseCase @Inject constructor(
    private val repository: ServerAreaRepository
){
    suspend operator fun invoke(areaCode: String?): Result<ArrayList<AreaItem>> {
        return repository.getAreaData(areaCode)
    }
}