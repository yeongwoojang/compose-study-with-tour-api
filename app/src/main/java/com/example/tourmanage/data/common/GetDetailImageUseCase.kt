package com.example.tourmanage.data.common

import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.domain.common.DetailRepository
import javax.inject.Inject

class GetDetailImageUseCase @Inject constructor(
    private val repository: DetailRepository
){
    suspend operator fun invoke(contentId: String): Result<ArrayList<DetailImageItem>> {
        return repository.getDetailImage(contentId)
    }
}