package com.projects.moviemanager.network.repository.home

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.search.SearchPageResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTrendingMulti(): Flow<Either<SearchPageResponse<MultiResponse>, ApiError>>
}
