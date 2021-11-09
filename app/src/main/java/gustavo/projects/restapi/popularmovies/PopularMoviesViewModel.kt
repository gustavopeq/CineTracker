package gustavo.projects.restapi.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gustavo.projects.restapi.Constants
import gustavo.projects.restapi.network.response.GetPopularMoviesByIdResponse

class PopularMoviesViewModel: ViewModel() {

    private val repository = PopularMoviesRepository()

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
            .setPageSize(Constants.PAGE_SIZE)
            .setPrefetchDistance(Constants.PREFETCH_DISTANCE)
            .build()

    private val dataSourceFactory = PopularMoviesDataSourceFactory(viewModelScope, repository)

    val moviesPagedListLiveData: LiveData<PagedList<GetPopularMoviesByIdResponse>> =
            LivePagedListBuilder(dataSourceFactory, pageListConfig).build()

}