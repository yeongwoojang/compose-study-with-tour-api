package com.example.tourmanage.data.search

import com.example.tourmanage.common.data.server.item.SearchItem
import com.example.tourmanage.common.repository.SearchAPI
import com.example.tourmanage.convertXY
import com.example.tourmanage.domain.search.GetSearchUseCase
import com.example.tourmanage.error.area.TourMangeException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchUseCaseImpl @Inject constructor(
    private val searchAPI: SearchAPI
): GetSearchUseCase {
    override suspend fun invoke(query: String): Result<Flow<SearchItem>> = runCatching{
        flow {
            val response = searchAPI.getSearchResult(query).response
            if (!response.isNullOrEmpty()) {
                emit(response[0].convertXY())
            } else {
                throw TourMangeException.SearchInfoNullException("검색결과가 없습니다.")
            }
        }
    }.onFailure {
        throw TourMangeException.SearchInfoNullException(it.message.orEmpty())
    }
}