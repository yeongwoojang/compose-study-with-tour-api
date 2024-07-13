package com.example.tourmanage.common.repository

import com.example.tourmanage.common.data.server.info.SearchInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {
    companion object {
        val SEARCH_URL = "https://openapi.naver.com/v1/search/"
        val CLIENT_ID = "xNgFb2GYRUPD3tKg6aaj"
        val SECRET_KEY = "SOyv5zK2gY"
    }

    @GET("local")
    suspend fun getSearchResult(
        @Query("query") query: String
    ): SearchInfo
}