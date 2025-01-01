package com.example.tourmanage.domain.search

import com.example.tourmanage.common.data.server.item.SearchItem
import kotlinx.coroutines.flow.Flow

interface GetSearchUseCase {
    suspend operator fun invoke(query: String): Result<Flow<SearchItem>>
}