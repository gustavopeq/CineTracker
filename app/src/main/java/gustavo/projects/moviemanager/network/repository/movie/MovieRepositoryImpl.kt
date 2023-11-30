package gustavo.projects.moviemanager.network.repository.movie

import gustavo.projects.moviemanager.domain.models.util.MovieListType
import gustavo.projects.moviemanager.network.models.ApiError
import gustavo.projects.moviemanager.network.models.movie.MovieList
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
        movieListType: MovieListType,
        pageIndex: Int
    ): Flow<Either<MovieList, ApiError>> {
        return toApiResult {
            movieService.getMovieList(
                movieListType = movieListType.type,
                pageIndex = pageIndex,
                language = "en-US"
            )
        }.asFlow()
    }
}
