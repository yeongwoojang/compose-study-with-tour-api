package com.example.tourmanage.usecase.domain.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.toPosterItem

class TourPagingSource(
    private val serviceAPI: ServiceAPI,
    private val contentTypeId: Config.CONTENT_TYPE_ID,
): PagingSource<Int, PosterItem>() {
    override fun getRefreshKey(state: PagingState<Int, PosterItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PosterItem> {
        try {
            val pageNo = (params.key ?: 1)
            val numOfRows = (params.loadSize)

            val response = serviceAPI.requestTourInfo(
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