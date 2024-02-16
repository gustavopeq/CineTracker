package com.projects.moviemanager.network.services.search

import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.core.LanguageManager.getUserLanguage
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/multi?api_key=${Constants.API_KEY}")
    suspend fun searchMultiByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguage(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<MultiResponse>>

    @GET("search/movie?api_key=${Constants.API_KEY}")
    suspend fun searchMovieByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguage(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<MovieResponse>>

    @GET("search/tv?api_key=${Constants.API_KEY}")
    suspend fun searchShowByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguage(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<ShowResponse>>

    @GET("search/person?api_key=${Constants.API_KEY}")
    suspend fun searchPersonByQuery(
        @Query("query") query: String,
        @Query("include_adult") matureEnabled: Boolean = false,
        @Query("language") language: String = getUserLanguage(),
        @Query("page") pageIndex: Int
    ): Response<ContentPagingResponse<PersonResponse>>
}
