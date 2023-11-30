package gustavo.projects.moviemanager.network.repository.movie

import gustavo.projects.moviemanager.domain.models.util.MovieListType
import gustavo.projects.moviemanager.network.models.ApiError
import gustavo.projects.moviemanager.network.models.movie.MovieList
import gustavo.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieList(
        movieListType: MovieListType,
        pageIndex: Int
    ): Flow<Either<MovieList, ApiError>>
}
