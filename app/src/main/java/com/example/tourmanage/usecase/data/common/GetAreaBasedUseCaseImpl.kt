package com.example.tourmanage.usecase.data.common

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.usecase.domain.common.AreaBasedPagingSource
import com.example.tourmanage.usecase.domain.common.GetAreaBasedUseCase
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetAreaBasedUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI,
): GetAreaBasedUseCase {
    override suspend fun invoke(
        areaCode: String,
        sigunguCode: String,
        contentTypeId: Config.CONTENT_TYPE_ID)
    : Result<Flow<PagingData<PosterItem>>> = runCatching {
        val pagingSourceFactory = {
            AreaBasedPagingSource(
                serviceAPI = serviceAPI,
                contentTypeId = contentTypeId,
                areaCode = areaCode,
                sigunguCode = sigunguCode
            )
        }
        Pager(
            config = PagingConfig(
                pageSize = 10,
//                initialLoadSize = 10
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}