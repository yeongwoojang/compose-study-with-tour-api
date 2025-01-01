package com.example.tourmanage.usecase.domain.common

import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toDetailCommonItem
import com.example.tourmanage.usecase.data.common.GetDetailCommonUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailCommonUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetDetailCommonUseCase {
    override suspend fun invoke(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<DetailCommonItem?> = runCatching<DetailCommonItem?> {
        val response = serviceAPI.requestDetailCommonInfo(contentId = contentId, contentTypeId = contentTypeId.value)
        val detailCommon = response.toDetailCommonItem()
        detailCommon

    }.onFailure {
        throw TourMangeException.DetailCommonInfoNullException(it.message.orEmpty())
    }
}