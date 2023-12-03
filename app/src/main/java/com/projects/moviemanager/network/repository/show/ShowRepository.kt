package com.projects.moviemanager.network.repository.show

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.response.content.ContentListPageResponse
import com.projects.moviemanager.network.response.content.ShowApiResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowRepository {
    suspend fun getShowList(
        @Path("content_list_type") contentListType: String,
        @Query("page") pageIndex: Int
    ): Flow<Either<ContentListPageResponse<ShowApiResponse>, ApiError>>
}
