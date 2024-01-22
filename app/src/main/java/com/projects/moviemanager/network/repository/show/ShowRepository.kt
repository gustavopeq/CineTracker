package com.projects.moviemanager.network.repository.show

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.ContentListPageResponse
import com.projects.moviemanager.network.models.content.show.ShowApiResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    suspend fun getShowList(
        contentListType: String,
        pageIndex: Int
    ): Flow<Either<ContentListPageResponse<ShowApiResponse>, ApiError>>

    suspend fun getShowDetailsById(
        showId: Int
    ): Flow<Either<ShowApiResponse, ApiError>>

    suspend fun getShowCreditsById(
        showId: Int
    ): Flow<Either<ContentCreditsResponse, ApiError>>

    suspend fun getShowVideosById(
        showId: Int
    ): Flow<Either<VideosByIdResponse, ApiError>>

    suspend fun getSimilarShowsById(
        showId: Int
    ): Flow<Either<ContentListPageResponse<ShowApiResponse>, ApiError>>
}
