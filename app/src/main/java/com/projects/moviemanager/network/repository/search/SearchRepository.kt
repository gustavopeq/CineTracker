package com.projects.moviemanager.network.repository.search

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.search.SearchPageResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun onSearchMultiByQuery(
        query: String,
        page: Int
    ): Flow<Either<SearchPageResponse<MultiResponse>, ApiError>>

    suspend fun onSearchMovieByQuery(
        query: String,
        page: Int
    ): Flow<Either<SearchPageResponse<MovieResponse>, ApiError>>

    suspend fun onSearchShowByQuery(
        query: String,
        page: Int
    ): Flow<Either<SearchPageResponse<ShowResponse>, ApiError>>

    suspend fun onSearchPersonByQuery(
        query: String,
        page: Int
    ): Flow<Either<SearchPageResponse<PersonResponse>, ApiError>>
}
