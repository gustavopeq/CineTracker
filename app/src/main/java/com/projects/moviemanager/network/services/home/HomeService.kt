package com.projects.moviemanager.network.services.home

import com.projects.moviemanager.core.LanguageManager.getUserLanguageTag
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("trending/all/day")
    suspend fun getDayTrendingMulti(
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<MultiResponse>>

    @GET("trending/person/day")
    suspend fun getDayTrendingPerson(
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<PersonResponse>>

    @GET("discover/movie")
    suspend fun getMoviesComingSoon(
        @Query("language") language: String = getUserLanguageTag(),
        @Query("primary_release_date.gte") releaseDateGte: String,
        @Query("primary_release_date.lte") releaseDateLte: String
    ): Response<ContentPagingResponse<MovieResponse>>
}
