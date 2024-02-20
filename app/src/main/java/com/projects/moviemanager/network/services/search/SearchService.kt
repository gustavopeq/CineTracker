package com.projects.moviemanager.network.services.search

import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.core.LanguageManager.getUserLanguageTag
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/multi")
    suspend fun searchMultiByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguageTag(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<MultiResponse>>

    @GET("search/movie")
    suspend fun searchMovieByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguageTag(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<MovieResponse>>

    @GET("search/tv")
    suspend fun searchShowByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguageTag(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<ShowResponse>>

    @GET("search/person")
    suspend fun searchPersonByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguageTag(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<PersonResponse>>
}
