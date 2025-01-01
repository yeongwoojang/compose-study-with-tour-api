package com.example.tourmanage.domain.area.usecase

import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.domain.area.repository.ServerAreaRepository
import javax.inject.Inject

class GetAreaUseCase @Inject constructor(
    private val repository: ServerAreaRepository
){
    suspend operator fun invoke(areaCode: String?): Result<ArrayList<AreaItem>> {
        return repository.getAreaData(areaCode)
    }
}