package com.example.tourmanage.usecase.data.common

import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toLocationBasedList
import com.example.tourmanage.usecase.domain.common.GetLocationBasedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocationBasedUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetLocationBasedUseCase {
    override suspend fun invoke(
        contentTypeId: Config.CONTENT_TYPE_ID,
        mapX: String,
        mapY: String,
        radius: String?
    ): Result<Flow<ArrayList<LocationBasedItem>>> = runCatching{
        flow {
            val response = serviceAPI.requestLocationBasedList(
                contentTypeId = contentTypeId.value,
                mapX = mapX,
                mapY = mapY
            )
            val locationBasedList = response.toLocationBasedList()
            if (locationBasedList.isEmpty()) {
                throw TourMangeException.LocationBasedNullException("위치기반 데이터 조회 실패")
            } else {
                emit(locationBasedList)
            }
        }
    }.onFailure {
        throw TourMangeException.LocationBasedNullException(it.message.orEmpty())
    }
}