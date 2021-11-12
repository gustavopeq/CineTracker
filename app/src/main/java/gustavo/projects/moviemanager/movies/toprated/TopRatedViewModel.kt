package gustavo.projects.moviemanager.movies.toprated


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import gustavo.projects.moviemanager.util.Constants

class TopRatedViewModel: ViewModel() {

    private var pagingSource: TopRatedPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = TopRatedPagingSource()
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