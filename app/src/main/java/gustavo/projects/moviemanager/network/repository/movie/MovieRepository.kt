package gustavo.projects.moviemanager.network.repository.movie

import gustavo.projects.moviemanager.domain.models.util.ContentListType
import gustavo.projects.moviemanager.network.models.ApiError
import gustavo.projects.moviemanager.network.response.content.ContentListPageResponse
import gustavo.projects.moviemanager.network.response.content.MovieApiResponse
import gustavo.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(
        contentListType: ContentListType,
        pageIndex: Int
    ): Flow<Either<ContentListPageResponse<MovieApiResponse>, ApiError>>
}
