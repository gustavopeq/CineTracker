package com.projects.moviemanager.network.repository.movie

import com.projects.moviemanager.common.domain.models.util.ContentListType
import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(
        contentListType: ContentListType,
        pageIndex: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>>

    suspend fun getMovieDetailsById(
        movieId: Int
    ): Flow<Either<MovieResponse, ApiError>>

    suspend fun getMovieCreditsById(
        movieId: Int
    ): Flow<Either<ContentCreditsResponse, ApiError>>

    suspend fun getMovieVideosById(
        movieId: Int
    ): Flow<Either<VideosByIdResponse, ApiError>>

    suspend fun getRecommendationsMoviesById(
        movieId: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>>

    suspend fun getSimilarMoviesById(
        movieId: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>>
}
