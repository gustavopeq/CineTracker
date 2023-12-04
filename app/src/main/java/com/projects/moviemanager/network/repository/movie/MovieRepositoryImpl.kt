package com.projects.moviemanager.network.repository.movie

import com.projects.moviemanager.domain.models.util.ContentListType
import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.response.content.ContentListPageResponse
import com.projects.moviemanager.network.response.content.MovieApiResponse
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
    ): Flow<Either<ContentListPageResponse<MovieApiResponse>, ApiError>> {
        return toApiResult {
            movieService.getMovieList(
                movieListType = contentListType.type,
                pageIndex = pageIndex,
                language = "en-US"
            )
        }.asFlow()
    }

    override suspend fun getMovieDetailsById(
        movieId: Int
    ): Flow<Either<MovieApiResponse, ApiError>> {
        return toApiResult {
            movieService.getMovieDetailsById(
                movieId = movieId
            )
        }.asFlow()
    }
}
