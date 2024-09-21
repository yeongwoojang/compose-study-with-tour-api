package com.example.tourmanage.usecase.data.common

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.usecase.domain.common.GetTourInfoUseCase
import com.example.tourmanage.usecase.domain.common.TourPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTourInfoUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI,
): GetTourInfoUseCase {
    override suspend fun invoke(contentTypeId: Config.CONTENT_TYPE_ID): Result<Flow<PagingData<PosterItem>>> = runCatching {
        val pagingSourceFactory = {
            TourPagingSource(
                serviceAPI = serviceAPI,
                contentTypeId = contentTypeId,
            )
        }

        Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }
}