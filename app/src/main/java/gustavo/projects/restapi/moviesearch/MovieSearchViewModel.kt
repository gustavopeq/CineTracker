package gustavo.projects.restapi.moviesearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import gustavo.projects.restapi.Constants

class MovieSearchViewModel: ViewModel() {

    private var currentUserSearch: String = ""

    //Singleton used to ensure that we will always get a valid pagingSource
    private var pagingSource: MovieSearchPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = MovieSearchPagingSource(currentUserSearch) { searchException ->
                    Log.d("Print", searchException.toString())
                }
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

    fun submitSearchQuery(movieSearched: String) {
        currentUserSearch = movieSearched

        //Invalidate function will invoke Pager function to get a new paging source
        pagingSource?.invalidate()
    }
}