package com.example.tourmanage.usecase.data.area

import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.error.area.AreaException
import com.example.tourmanage.toAreaInfoList
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetAreaUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetAreaUseCase {
    override suspend fun invoke(areaCode: String?): Result<Flow<ArrayList<AreaItem>>> = runCatching{
        flow {
            val response = serviceAPI.requestAreaList(
                areaCode = areaCode
            )
            val areaCodes = response.toAreaInfoList()
            Timber.i("GetAreaUseCase() | areaCodes: $areaCodes")
            if (areaCodes.isEmpty()) {
                throw AreaException("지역코드 없음.")
            } else {
                ServerGlobal.setMainAreaList(areaCodes)
                emit(areaCodes)
            }
        }
    }.onFailure {
        throw AreaException(it.message.orEmpty())
    }
}