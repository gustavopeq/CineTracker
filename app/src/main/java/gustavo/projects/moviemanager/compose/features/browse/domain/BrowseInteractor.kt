package gustavo.projects.moviemanager.compose.features.browse.domain

import gustavo.projects.moviemanager.domain.mappers.MovieMapper
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.domain.models.util.MovieListType
import gustavo.projects.moviemanager.network.repository.movie.MovieRepository
import gustavo.projects.moviemanager.network.util.Left
import gustavo.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

class BrowseInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovieList(
        movieListType: MovieListType,
        pageIndex: Int
    ): List<Movie> {
        val result = movieRepository.getMovieList(movieListType, pageIndex)
        var listOfMovies = listOf<Movie>()
        result.collect { response ->
            when (response) {
                is Right -> Timber.e("getMovieList failed with error: ${response.error}")
                is Left -> {
                    listOfMovies = response.value.results.mapNotNull {
                        MovieMapper.buildFrom(it)
                    }
                }
            }
        }
        return listOfMovies
    }
}
