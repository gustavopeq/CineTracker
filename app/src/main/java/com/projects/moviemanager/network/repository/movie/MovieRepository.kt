package com.projects.moviemanager.network.repository.movie

import com.projects.moviemanager.domain.models.util.ContentListType
import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.response.content.ContentListPageResponse
import com.projects.moviemanager.network.response.content.MovieApiResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(
        contentListType: ContentListType,
        pageIndex: Int
    ): Flow<Either<ContentListPageResponse<MovieApiResponse>, ApiError>>
}
