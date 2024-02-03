package com.projects.moviemanager.network.repository.movie

import com.projects.moviemanager.common.domain.models.util.ContentListType
import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.ContentListPageResponse
import com.projects.moviemanager.network.models.content.movie.MovieApiResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(
        contentListType: ContentListType,
        pageIndex: Int
    ): Flow<Either<ContentListPageResponse<MovieApiResponse>, ApiError>>

    suspend fun getMovieDetailsById(
        movieId: Int
    ): Flow<Either<MovieApiResponse, ApiError>>

    suspend fun getMovieCreditsById(
        movieId: Int
    ): Flow<Either<ContentCreditsResponse, ApiError>>

    suspend fun getMovieVideosById(
        movieId: Int
    ): Flow<Either<VideosByIdResponse, ApiError>>

    suspend fun getSimilarMoviesById(
        movieId: Int
    ): Flow<Either<ContentListPageResponse<MovieApiResponse>, ApiError>>
}
