package com.projects.moviemanager.network.repository.movie

import com.projects.moviemanager.common.domain.models.util.ContentListType
import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.ContentCreditsResponse
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.VideosByIdResponse
import com.projects.moviemanager.network.models.content.common.WatchProvidersResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.services.movie.MovieService
import com.projects.moviemanager.network.util.Either
import com.projects.moviemanager.network.util.asFlow
import com.projects.moviemanager.network.util.toApiResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovieList(
        contentListType: ContentListType,
        pageIndex: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>> {
        return toApiResult {
            movieService.getMovieList(
                movieListType = contentListType.type,
                pageIndex = pageIndex
            )
        }.asFlow()
    }

    override suspend fun getMovieDetailsById(
        movieId: Int
    ): Flow<Either<MovieResponse, ApiError>> {
        return toApiResult {
            movieService.getMovieDetailsById(
                movieId = movieId
            )
        }.asFlow()
    }

    override suspend fun getMovieCreditsById(
        movieId: Int
    ): Flow<Either<ContentCreditsResponse, ApiError>> {
        return toApiResult {
            movieService.getMovieCreditsById(
                movieId = movieId
            )
        }.asFlow()
    }

    override suspend fun getMovieVideosById(
        movieId: Int
    ): Flow<Either<VideosByIdResponse, ApiError>> {
        return toApiResult {
            movieService.getMovieVideosById(
                movieId = movieId
            )
        }.asFlow()
    }

    override suspend fun getRecommendationsMoviesById(
        movieId: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>> {
        return toApiResult {
            movieService.getRecommendationsMoviesById(
                movieId = movieId
            )
        }.asFlow()
    }

    override suspend fun getSimilarMoviesById(
        movieId: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>> {
        return toApiResult {
            movieService.getSimilarMoviesById(
                movieId = movieId
            )
        }.asFlow()
    }

    override suspend fun getStreamingProviders(
        movieId: Int
    ): Flow<Either<WatchProvidersResponse, ApiError>> {
        return toApiResult {
            movieService.getStreamingProviders(
                movieId = movieId
            )
        }.asFlow()
    }
}
