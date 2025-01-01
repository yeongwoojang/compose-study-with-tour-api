package com.example.tourmanage.domain.common

import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.common.GetDetailInfoUseCase
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toDetailItems
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailInfoUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
) : GetDetailInfoUseCase {
    override suspend fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<ArrayList<DetailItem>> = runCatching {
        val response = serviceAPI.requestDetailInfo(contentId = contentId, contentTypeId = contentTypeId.value)
        val detailItems = response.toDetailItems()
        detailItems
    }.onFailure {
        throw TourMangeException.DetailInfoNullException("정보 없음.")
    }
}