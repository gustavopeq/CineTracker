package gustavo.projects.moviemanager.network.repository.movie

import gustavo.projects.moviemanager.domain.models.util.ContentListType
import gustavo.projects.moviemanager.network.models.ApiError
import gustavo.projects.moviemanager.network.response.content.ContentListPageResponse
import gustavo.projects.moviemanager.network.response.content.MovieApiResponse
import gustavo.projects.moviemanager.network.services.movie.MovieService
import gustavo.projects.moviemanager.network.util.Either
import gustavo.projects.moviemanager.network.util.asFlow
import gustavo.projects.moviemanager.network.util.toApiResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovieList(
        contentListType: ContentListType,
        pageIndex: Int
    ): Flow<Either<ContentListPageResponse<MovieApiResponse>, ApiError>> {
        return toApiResult {
            movieService.getMovieList(
                movieListType = contentListType.type,
                pageIndex = pageIndex,
                language = "en-US"
            )
        }.asFlow()
    }
}
