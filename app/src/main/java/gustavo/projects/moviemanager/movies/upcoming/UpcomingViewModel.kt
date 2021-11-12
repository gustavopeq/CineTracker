package gustavo.projects.moviemanager.movies.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import gustavo.projects.moviemanager.util.Constants

class UpcomingViewModel: ViewModel() {

    private var pagingSource: UpcomingPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = UpcomingPagingSource()
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