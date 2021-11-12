package gustavo.projects.moviemanager.movies.nowplaying


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import gustavo.projects.moviemanager.util.Constants

class NowPlayingViewModel: ViewModel() {

    private var pagingSource: NowPlayingPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = NowPlayingPagingSource()
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