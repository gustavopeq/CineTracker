package gustavo.projects.moviemanager.network.repository.show

import gustavo.projects.moviemanager.network.models.ApiError
import gustavo.projects.moviemanager.network.response.content.ContentListPageResponse
import gustavo.projects.moviemanager.network.response.content.ShowApiResponse
import gustavo.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowRepository {
    suspend fun getShowList(
        @Path("content_list_type") contentListType: String,
        @Query("page") pageIndex: Int
    ): Flow<Either<ContentListPageResponse<ShowApiResponse>, ApiError>>
}
