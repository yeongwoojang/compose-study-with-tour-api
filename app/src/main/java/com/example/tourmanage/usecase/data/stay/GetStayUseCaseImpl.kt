package com.example.tourmanage.usecase.data.stay

import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toPosterItem
import com.example.tourmanage.usecase.domain.stay.GetStayUseCase
import javax.inject.Inject

class GetStayUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetStayUseCase {
    override suspend fun invoke(areaCode: String?, sigunguCode: String?): Result<List<PosterItem>> = runCatching{
        val response = serviceAPI.requestSearchStay(areaCode = areaCode, sigunguCode = sigunguCode)

        response.toPosterItem().ifEmpty {
            throw TourMangeException.StayInfoNullException("숙소 목록을 불러올 수 없습니다.")
        }
    }.onFailure {
        throw TourMangeException.StayInfoNullException(it.message.orEmpty())
    }
}