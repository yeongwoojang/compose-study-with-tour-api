package com.example.tourmanage.domain.common

import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.data.common.GetDetailImageUseCase
import com.example.tourmanage.toDetailImageList
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