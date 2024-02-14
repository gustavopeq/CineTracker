package com.projects.moviemanager.network.repository.show

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.network.models.content.common.WatchProvidersResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    suspend fun getShowList(
        contentListType: String,
        pageIndex: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>>

    suspend fun getShowDetailsById(
        showId: Int
    ): Flow<Either<ShowResponse, ApiError>>

    suspend fun getShowCreditsById(
        showId: Int
    ): Flow<Either<ContentCreditsResponse, ApiError>>

    suspend fun getShowVideosById(
        showId: Int
    ): Flow<Either<VideosByIdResponse, ApiError>>

    suspend fun getRecommendationsShowsById(
        showId: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>>

    suspend fun getSimilarShowsById(
        showId: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>>

    suspend fun getStreamingProviders(
        showId: Int
    ): Flow<Either<WatchProvidersResponse, ApiError>>
}
