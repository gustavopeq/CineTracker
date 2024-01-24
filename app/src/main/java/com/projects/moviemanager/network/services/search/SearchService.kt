package com.projects.moviemanager.network.services.search

import com.projects.moviemanager.network.models.search.SearchPageResponse
import com.projects.moviemanager.util.Constants
import com.projects.moviemanager.util.Constants.ENGLISH_LANGUAGE_CODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/multi?api_key=${Constants.API_KEY}")
    suspend fun searchMultiQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = true,
        @Query("language") language: String = ENGLISH_LANGUAGE_CODE,
        @Query("page") pageIndex: Int
    ): Response<SearchPageResponse>
}
