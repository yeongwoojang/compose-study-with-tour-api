package com.example.tourmanage.usecase.domain.common

import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toDetailImageLise
import com.example.tourmanage.usecase.data.common.GetDetailImageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailImageUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetDetailImageUseCase {
    override suspend fun invoke(contentId: String): Result<Flow<ArrayList<DetailImageItem>>> = runCatching<Flow<ArrayList<DetailImageItem>>> {
        flow {
            val response = serviceAPI.requestDetailImage(contentId = contentId)
            val detailImages = response.toDetailImageLise()
            emit(detailImages)
        }

    }.onFailure {
        throw TourMangeException.DetailImageInfoNullException(it.message.orEmpty())
    }
}