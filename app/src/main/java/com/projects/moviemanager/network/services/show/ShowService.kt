package com.projects.moviemanager.network.services.show

import com.projects.moviemanager.network.response.content.ContentListPageResponse
import com.projects.moviemanager.network.response.content.ShowApiResponse
import com.projects.moviemanager.util.Constants
import com.projects.moviemanager.util.Constants.ENGLISH_LANGUAGE_CODE
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
}
