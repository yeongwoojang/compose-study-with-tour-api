package com.example.tourmanage.data.common

import com.example.tourmanage.common.data.server.item.DetailImageItem

interface GetDetailImageUseCase {
    suspend operator fun invoke(contentId: String): Result<ArrayList<DetailImageItem>>
}