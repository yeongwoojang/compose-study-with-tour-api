package com.example.tourmanage.usecase.data.stay

import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toPosterItem
import com.example.tourmanage.toStayInfoList
import com.example.tourmanage.usecase.domain.stay.GetStayUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStayUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetStayUseCase {
    override suspend fun invoke(areaCode: String?, sigunguCode: String?): Result<Flow<List<PosterItem>>> = runCatching{
        flow {
            val response = serviceAPI.requestSearchStay(areaCode = areaCode, sigunguCode = sigunguCode)
            val stayInfo = response.toPosterItem()
            if (stayInfo.isEmpty()) {
                throw TourMangeException.StayInfoNullException("숙소 목록을 불러올 수 없습니다.")
            } else {
                emit(stayInfo)
            }
        }
    }.onFailure {
        throw TourMangeException.StayInfoNullException(it.message.orEmpty())
    }
}