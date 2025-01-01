package com.example.tourmanage.domain.common

import androidx.paging.PagingData
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow

interface AreaBasedRepository {
    suspend fun getAreaBasedData(
        areaCode: String,
        sigunguCode: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<Flow<PagingData<PosterItem>>>
}