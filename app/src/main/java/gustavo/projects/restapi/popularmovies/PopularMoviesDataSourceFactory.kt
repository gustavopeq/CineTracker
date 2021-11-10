package gustavo.projects.restapi.popularmovies

import androidx.paging.DataSource
import gustavo.projects.restapi.domain.models.PopularMovie
import kotlinx.coroutines.CoroutineScope

class PopularMoviesDataSourceFactory(
        private val coroutineScope: CoroutineScope,
        private val repository: PopularMoviesRepository
): DataSource.Factory<Int, PopularMovie>() {

    override fun create(): DataSource<Int, PopularMovie> {
        return PopularMoviesDataSource(coroutineScope, repository)
    }


}