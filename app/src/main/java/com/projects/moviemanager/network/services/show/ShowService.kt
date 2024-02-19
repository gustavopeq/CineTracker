package com.projects.moviemanager.network.services.show

import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.core.LanguageManager.getUserLanguageTag
import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.network.models.content.common.WatchProvidersResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {
    @GET("tv/{content_list_type}")
    suspend fun getShowList(
        @Path("content_list_type") contentListType: String,
        @Query("page") pageIndex: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<ShowResponse>>

    @GET("tv/{show_ID}")
    suspend fun getShowDetailsById(
        @Path("show_ID") showId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ShowResponse>

    @GET("tv/{show_ID}/aggregate_credits")
    suspend fun getShowCreditsById(
        @Path("show_ID") showId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentCreditsResponse>

    @GET("tv/{show_ID}/videos")
    suspend fun getShowVideosById(
        @Path("show_ID") showId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<VideosByIdResponse>

    @GET("tv/{show_ID}/recommendations")
    suspend fun getRecommendationsShowsById(
        @Path("show_ID") showId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<ShowResponse>>

    @GET("tv/{show_ID}/similar")
    suspend fun getSimilarShowsById(
        @Path("show_ID") showId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<ContentPagingResponse<ShowResponse>>

    @GET("tv/{show_ID}/watch/providers")
    suspend fun getStreamingProviders(
        @Path("show_ID") showId: Int,
        @Query("language") language: String = getUserLanguageTag()
    ): Response<WatchProvidersResponse>
}
