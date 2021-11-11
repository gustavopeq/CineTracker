package gustavo.projects.moviemanager.movies.popularmovies

import androidx.paging.DataSource
import gustavo.projects.moviemanager.domain.models.Movie
import kotlinx.coroutines.CoroutineScope

class PopularMoviesDataSourceFactory(
        private val coroutineScope: CoroutineScope,
        private val repository: PopularMoviesRepository
): DataSource.Factory<Int, Movie>() {

    override fun create(): DataSource<Int, Movie> {
        return PopularMoviesDataSource(coroutineScope, repository)
    }


}