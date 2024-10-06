package com.example.tourmanage.usecase.data.common

import com.example.tourmanage.common.data.server.item.DetailIntroItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toDetailInfoList
import com.example.tourmanage.usecase.domain.common.GetDetailIntroUseCase
import javax.inject.Inject

class GetDetailIntroUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetDetailIntroUseCase {
    override suspend fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<List<DetailIntroItem>> = runCatching {
        val response = serviceAPI.requestDetailIntro(
            contentId = contentId,
            contentTypeId = contentTypeId.value
        )

        val introItems = response.toDetailInfoList()
        introItems.ifEmpty {
            throw TourMangeException.DetailIntroNullException("인트로 정보 조회를 실패했습니다.")
        }

    }.onFailure {
        throw TourMangeException.DetailIntroNullException(it.message.orEmpty())
    }
}