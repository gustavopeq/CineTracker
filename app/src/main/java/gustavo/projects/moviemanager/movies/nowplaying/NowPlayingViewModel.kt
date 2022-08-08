package gustavo.projects.moviemanager.movies.nowplaying


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.network.ApiClient
import gustavo.projects.moviemanager.util.Constants
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val apiClient: ApiClient
): ViewModel() {

    private var pagingSource: NowPlayingPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = NowPlayingPagingSource(apiClient)
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