package com.projects.moviemanager.network.services.movie

import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.core.LanguageManager.getUserLanguageTag
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.WatchProvidersResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/{movie_list_type}")
    suspend fun getMovieList(
        @Path("movie_list_type") movieListType: String,
        @Query("page") pageIndex: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<MovieResponse>>

    @GET("movie/{movie_ID}")
    suspend fun getMovieDetailsById(
        @Path("movie_ID") movieId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<MovieResponse>

    @GET("movie/{movie_ID}/credits")
    suspend fun getMovieCreditsById(
        @Path("movie_ID") movieId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentCreditsResponse>

    @GET("movie/{movie_ID}/videos")
    suspend fun getMovieVideosById(
        @Path("movie_ID") movieId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<VideosByIdResponse>

    @GET("movie/{movie_ID}/recommendations")
    suspend fun getRecommendationsMoviesById(
        @Path("movie_ID") movieId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<MovieResponse>>

    @GET("movie/{movie_ID}/similar")
    suspend fun getSimilarMoviesById(
        @Path("movie_ID") movieId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<MovieResponse>>

    @GET("movie/{movie_ID}/watch/providers")
    suspend fun getStreamingProviders(
        @Path("movie_ID") movieId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<WatchProvidersResponse>
}
