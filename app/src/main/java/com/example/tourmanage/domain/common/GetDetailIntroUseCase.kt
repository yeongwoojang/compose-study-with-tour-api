package com.example.tourmanage.domain.common

import com.example.tourmanage.common.data.server.item.DetailIntroItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.value.Config
import javax.inject.Inject

class GetDetailIntroUseCase @Inject constructor(
    private val repository: DetailRepository
) {
       suspend operator fun invoke(contentId: String, contentTypeId: Config.CONTENT_TYPE_ID): Result<List<DetailIntroItem>> {
           return repository.getDetailIntro(contentId, contentTypeId)
       }
}