package com.example.tourmanage.usecase.domain.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.toPosterItem
import timber.log.Timber

class AreaBasedPagingSource(
    private val serviceAPI: ServiceAPI,
    private val contentTypeId: Config.CONTENT_TYPE_ID,
    private val areaCode: String,
    private val sigunguCode: String,
): PagingSource<Int, PosterItem>() {
    override fun getRefreshKey(state: PagingState<Int, PosterItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PosterItem> {
        try {
            val pageNo = (params.key ?: 1)
            val numOfRows = (params.loadSize)
            val response = serviceAPI.requestAreaBasedList(
                areaCode = areaCode,
                sigunguCode = sigunguCode,
                contentTypeId = contentTypeId.value,
                numOfRows = numOfRows.toString(),
                pageNo = pageNo.toString()
            )
            val size = response.response?.body?.items?.item?.size ?: 0
            return LoadResult.Page(
                data = response.toPosterItem(),
                prevKey = null,
                nextKey = if (size == numOfRows) pageNo + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}