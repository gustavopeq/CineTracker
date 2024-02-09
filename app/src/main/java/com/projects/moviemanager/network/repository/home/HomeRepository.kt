package com.projects.moviemanager.network.repository.home

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTrendingMulti(): Flow<Either<ContentPagingResponse<MultiResponse>, ApiError>>
    suspend fun getTrendingPerson(): Flow<Either<ContentPagingResponse<PersonResponse>, ApiError>>
    suspend fun getMoviesComingSoon(
        releaseDateGte: String,
        releaseDateLte: String
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>>
}
