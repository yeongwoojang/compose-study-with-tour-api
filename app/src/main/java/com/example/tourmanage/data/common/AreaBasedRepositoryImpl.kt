package com.example.tourmanage.data.common

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.domain.common.AreaBasedPagingSource
import com.example.tourmanage.domain.common.AreaBasedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AreaBasedRepositoryImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): AreaBasedRepository {
    override suspend fun getAreaBasedData(
        areaCode: String,
        sigunguCode: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<Flow<PagingData<PosterItem>>>  = runCatching {
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
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}