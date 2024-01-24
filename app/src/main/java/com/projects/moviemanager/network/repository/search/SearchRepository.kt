package com.projects.moviemanager.network.repository.search

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.search.SearchPageResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun onSearchQuery(
        query: String,
        page: Int
    ): Flow<Either<SearchPageResponse, ApiError>>
}
