package com.example.tourmanage.usecase.domain.common

import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toDetailImageList
import com.example.tourmanage.usecase.data.common.GetDetailImageUseCase
import javax.inject.Inject

class GetDetailImageUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetDetailImageUseCase {
    override suspend fun invoke(contentId: String): Result<ArrayList<DetailImageItem>> = runCatching<ArrayList<DetailImageItem>> {
        val response = serviceAPI.requestDetailImage(contentId = contentId)
        val detailImages = response.toDetailImageList()

        detailImages
    }
}