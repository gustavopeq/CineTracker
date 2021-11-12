package gustavo.projects.moviemanager.movies.popular


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import gustavo.projects.moviemanager.util.Constants

class PopularMoviesViewModel: ViewModel() {

    private var pagingSource: PopularMoviesPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = PopularMoviesPagingSource()
            }
            return field
        }

    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

}