package com.example.tourmanage.usecase.data.common

import com.example.tourmanage.common.data.server.item.DetailImageItem

interface GetDetailImageUseCase {
    suspend operator fun invoke(contentId: String): Result<ArrayList<DetailImageItem>>
}