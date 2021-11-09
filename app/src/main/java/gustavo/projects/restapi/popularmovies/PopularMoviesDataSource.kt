package gustavo.projects.restapi.popularmovies

import androidx.paging.PageKeyedDataSource
import gustavo.projects.restapi.network.response.GetPopularMoviesByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PopularMoviesDataSource(
        private val coroutineScope: CoroutineScope,
        private val repository: PopularMoviesRepository
        ): PageKeyedDataSource<Int, GetPopularMoviesByIdResponse>() {

        override fun loadInitial(
                params: LoadInitialParams<Int>,
                callback: LoadInitialCallback<Int, GetPopularMoviesByIdResponse>
        ) {
                coroutineScope.launch {
                        val page = repository.getPopularMoviesPage(1)

                        if(page == null) {
                                callback.onResult(emptyList(), null, null)
                                return@launch
                        }

                        callback.onResult(page.results, null, page.page + 1)
                }
        }

        override fun loadBefore(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, GetPopularMoviesByIdResponse>
        ) {
                //Nothing to do
        }

        override fun loadAfter(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, GetPopularMoviesByIdResponse>) {
                coroutineScope.launch {
                        val page = repository.getPopularMoviesPage(params.key)

                        if(page == null) {
                                callback.onResult(emptyList(), null)
                                return@launch
                        }

                        callback.onResult(page.results, page.page + 1)
                }
        }
}