package gustavo.projects.moviemanager.compose.features.browse.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import gustavo.projects.moviemanager.compose.features.browse.ui.paging.MoviePagingSource
import gustavo.projects.moviemanager.domain.models.movie.Movie
import gustavo.projects.moviemanager.domain.models.util.MovieListType
import gustavo.projects.moviemanager.network.repository.movie.MovieRepository
import gustavo.projects.moviemanager.util.Constants.PAGE_SIZE
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class BrowseInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getMovieListPager(
        movieListType: MovieListType
    ): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            MoviePagingSource(movieRepository, movieListType)
        }.flow
    }
}
