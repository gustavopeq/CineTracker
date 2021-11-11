package gustavo.projects.moviemanager.movies.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.domain.models.Movie

class PopularMoviesViewModel: ViewModel() {

    private val repository = PopularMoviesRepository()

    private val pageListConfig: PagedList.Config = PagedList.Config.Builder()
            .setPageSize(Constants.PAGE_SIZE)
            .setPrefetchDistance(Constants.PREFETCH_DISTANCE)
            .build()

    private val dataSourceFactory = PopularMoviesDataSourceFactory(viewModelScope, repository)

    val moviesPagedListLiveData: LiveData<PagedList<Movie>> =
            LivePagedListBuilder(dataSourceFactory, pageListConfig).build()

}