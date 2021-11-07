package gustavo.projects.restapi.movies

import androidx.paging.PageKeyedDataSource
import gustavo.projects.restapi.MoviesRepository
import gustavo.projects.restapi.network.response.GetPopularMovieByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MoviesDataSource(
        private val coroutineScope: CoroutineScope,
        private val repository: MoviesRepository
        ): PageKeyedDataSource<Int, GetPopularMovieByIdResponse>() {
        override fun loadInitial(
                params: LoadInitialParams<Int>,
                callback: LoadInitialCallback<Int, GetPopularMovieByIdResponse>
        ) {
                coroutineScope.launch {
                        val page = repository.getPopularMoviesList(1)

                        if(page == null) {
                                callback.onResult(emptyList(), null, null)
                                return@launch
                        }

                        callback.onResult(page.results, null, page.page + 1)
                }
        }

        override fun loadBefore(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, GetPopularMovieByIdResponse>
        ) {
                coroutineScope.launch {
                        val page = repository.getPopularMoviesList(params.key)

                        if(page == null) {
                                callback.onResult(emptyList(), null)
                                return@launch
                        }

                        callback.onResult(page.results, page.page + 1)
                }

        }

        override fun loadAfter(
                params: LoadParams<Int>,
                callback: LoadCallback<Int, GetPopularMovieByIdResponse>) {
                // Nothing to do
        }
}