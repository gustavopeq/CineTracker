package com.projects.moviemanager.network.services.home

import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.search.SearchPageResponse
import com.projects.moviemanager.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("trending/all/day?api_key=${Constants.API_KEY}")
    suspend fun getDayTrendingMulti(
        @Query("language") language: String = Constants.ENGLISH_LANGUAGE_CODE
    ): Response<SearchPageResponse<MultiResponse>>
}
