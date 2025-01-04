package com.example.tourmanage.data.common

import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.domain.common.DetailRepository
import javax.inject.Inject

class GetDetailInfoUseCase @Inject constructor(
    private val repository: DetailRepository
) {
    suspend operator fun invoke(contentId: String, contentTypeId: Config.CONTENT_TYPE_ID): Result<ArrayList<DetailItem>> {
        return repository.getDetailInfo(contentId, contentTypeId)
    }
}