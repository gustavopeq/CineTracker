package com.projects.moviemanager.network.services.show

import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.ContentListPageResponse
import com.projects.moviemanager.network.models.content.show.ShowApiResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.common.util.Constants.ENGLISH_LANGUAGE_CODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {
    @GET("tv/{content_list_type}?api_key=${Constants.API_KEY}")
    suspend fun getShowList(
        @Path("content_list_type") contentListType: String,
        @Query("page") pageIndex: Int,
        @Query("language") language: String
    ): Response<ContentListPageResponse<ShowApiResponse>>

    @GET("tv/{show_ID}?api_key=${Constants.API_KEY}")
    suspend fun getShowDetailsById(
        @Path("show_ID") showId: Int,
        @Query("language") language: String = ENGLISH_LANGUAGE_CODE
    ): Response<ShowApiResponse>

    @GET("tv/{show_ID}/credits?api_key=${Constants.API_KEY}")
    suspend fun getShowCreditsById(
        @Path("show_ID") showId: Int
    ): Response<ContentCreditsResponse>

    @GET("tv/{show_ID}/videos?api_key=${Constants.API_KEY}")
    suspend fun getShowVideosById(
        @Path("show_ID") showId: Int
    ): Response<VideosByIdResponse>

    @GET("tv/{show_ID}/recommendations?api_key=${Constants.API_KEY}")
    suspend fun getRecommendationsShowsById(
        @Path("show_ID") showId: Int
    ): Response<ContentListPageResponse<ShowApiResponse>>

    @GET("tv/{show_ID}/similar?api_key=${Constants.API_KEY}")
    suspend fun getSimilarShowsById(
        @Path("show_ID") showId: Int
    ): Response<ContentListPageResponse<ShowApiResponse>>
}
