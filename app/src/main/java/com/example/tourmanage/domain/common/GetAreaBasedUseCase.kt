package com.example.tourmanage.domain.common

import androidx.paging.PagingData
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAreaBasedUseCase @Inject constructor(
    private val repository: AreaBasedRepository
) {
    suspend operator fun invoke(
        areaCode: String,
        sigunguCode: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<Flow<PagingData<PosterItem>>> {
        return repository.getAreaBasedData(areaCode, sigunguCode, contentTypeId)
    }
}