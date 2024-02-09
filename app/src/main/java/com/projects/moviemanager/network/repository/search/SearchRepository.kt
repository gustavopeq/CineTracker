package com.projects.moviemanager.network.repository.search

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun onSearchMultiByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<MultiResponse>, ApiError>>

    suspend fun onSearchMovieByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>>

    suspend fun onSearchShowByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>>

    suspend fun onSearchPersonByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<PersonResponse>, ApiError>>
}
