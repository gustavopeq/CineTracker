package gustavo.projects.restapi.popularmovies

import androidx.paging.DataSource
import gustavo.projects.restapi.network.response.GetPopularMoviesByIdResponse
import kotlinx.coroutines.CoroutineScope

class PopularMoviesDataSourceFactory(
        private val coroutineScope: CoroutineScope,
        private val repository: PopularMoviesRepository
): DataSource.Factory<Int, GetPopularMoviesByIdResponse>() {

    override fun create(): DataSource<Int, GetPopularMoviesByIdResponse> {
        return PopularMoviesDataSource(coroutineScope, repository)
    }


}