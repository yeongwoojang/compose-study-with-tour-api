package com.example.tourmanage.usecase.data.common

import com.example.tourmanage.common.data.server.item.DetailImageItem
import kotlinx.coroutines.flow.Flow

interface GetDetailImageUseCase {
    suspend operator fun invoke(contentId: String): Result<Flow<ArrayList<DetailImageItem>>>
}