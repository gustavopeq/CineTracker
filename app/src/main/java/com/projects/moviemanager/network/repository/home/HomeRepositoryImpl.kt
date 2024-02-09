package com.projects.moviemanager.network.repository.home

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.services.home.HomeService
import com.projects.moviemanager.network.util.Either
import com.projects.moviemanager.network.util.asFlow
import com.projects.moviemanager.network.util.toApiResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {
    override suspend fun getTrendingMulti():
        Flow<Either<ContentPagingResponse<MultiResponse>, ApiError>> {
        return toApiResult {
            homeService.getDayTrendingMulti()
        }.asFlow()
    }

    override suspend fun getTrendingPerson():
        Flow<Either<ContentPagingResponse<PersonResponse>, ApiError>> {
        return toApiResult {
            homeService.getDayTrendingPerson()
        }.asFlow()
    }

    override suspend fun getMoviesComingSoon(
        releaseDateGte: String,
        releaseDateLte: String
    ): Flow<Either<ContentPagingResponse<MovieResponse>, ApiError>> {
        return toApiResult {
            homeService.getMoviesComingSoon(
                releaseDateGte = releaseDateGte,
                releaseDateLte = releaseDateLte
            )
        }.asFlow()
    }
}
