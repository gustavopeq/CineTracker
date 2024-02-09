package com.projects.moviemanager.network.repository.search

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.services.search.SearchService
import com.projects.moviemanager.network.util.Either
import com.projects.moviemanager.network.util.asFlow
import com.projects.moviemanager.network.util.toApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRepository {
    override suspend fun onSearchMultiByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<MultiResponse>, ApiError>> {
        return toApiResult {
            searchService.searchMultiByQuery(
                query = query,
                pageIndex = page
            )
        }.asFlow()
    }

    override suspend fun onSearchMovieByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>> {
        return toApiResult {
            searchService.searchMovieByQuery(
                query = query,
                pageIndex = page
            )
        }.asFlow()
    }

    override suspend fun onSearchShowByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<ShowResponse>, ApiError>> {
        return toApiResult {
            searchService.searchShowByQuery(
                query = query,
                pageIndex = page
            )
        }.asFlow()
    }

    override suspend fun onSearchPersonByQuery(
        query: String,
        page: Int
    ): Flow<Either<ContentPagingResponse<PersonResponse>, ApiError>> {
        return toApiResult {
            searchService.searchPersonByQuery(
                query = query,
                pageIndex = page
            )
        }.asFlow()
    }
}
