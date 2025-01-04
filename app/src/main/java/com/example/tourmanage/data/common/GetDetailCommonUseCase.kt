package com.example.tourmanage.data.common

import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.domain.common.DetailRepository
import javax.inject.Inject

class GetDetailCommonUseCase @Inject constructor(
    private val repository: DetailRepository
){
    suspend operator fun invoke(contentId: String, contentTypeId: Config.CONTENT_TYPE_ID): Result<DetailCommonItem?> {
        return repository.getDetailCommon(contentId, contentTypeId)
    }
}