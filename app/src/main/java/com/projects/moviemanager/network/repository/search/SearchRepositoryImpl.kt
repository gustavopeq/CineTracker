package com.projects.moviemanager.network.repository.search

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.search.SearchPageResponse
import com.projects.moviemanager.network.services.search.SearchService
import com.projects.moviemanager.network.util.Either
import com.projects.moviemanager.network.util.asFlow
import com.projects.moviemanager.network.util.toApiResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRepository {
    override suspend fun onSearchQuery(
        query: String,
        page: Int
    ): Flow<Either<SearchPageResponse, ApiError>> {
        return toApiResult {
            searchService.searchMultiQuery(
                query = query,
                pageIndex = page
            )
        }.asFlow()
    }
}
