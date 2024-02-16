package com.projects.moviemanager.network.services.home

import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.core.LanguageManager.getUserLanguage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("trending/all/day?api_key=${Constants.API_KEY}")
    suspend fun getDayTrendingMulti(
        @Query("language") language: String = getUserLanguage()
    ): Response<ContentPagingResponse<MultiResponse>>

    @GET("trending/person/day?api_key=${Constants.API_KEY}")
    suspend fun getDayTrendingPerson(
        @Query("language") language: String = getUserLanguage()
    ): Response<ContentPagingResponse<PersonResponse>>

    @GET("discover/movie?api_key=${Constants.API_KEY}")
    suspend fun getMoviesComingSoon(
        @Query("language") language: String = getUserLanguage(),
        @Query("primary_release_date.gte") releaseDateGte: String,
        @Query("primary_release_date.lte") releaseDateLte: String
    ): Response<ContentPagingResponse<MovieResponse>>
}
