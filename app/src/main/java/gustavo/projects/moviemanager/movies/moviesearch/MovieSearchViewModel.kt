package gustavo.projects.moviemanager.movies.moviesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.Event
import gustavo.projects.moviemanager.movies.moviesearch.MovieSearchPagingSource.SearchException

class MovieSearchViewModel: ViewModel() {

    private var currentUserSearch: String = ""


    //Singleton used to ensure that we will always get a valid pagingSource
    private var pagingSource: MovieSearchPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = MovieSearchPagingSource(currentUserSearch) { searchException ->

                    _searchExceptionEventLiveData.postValue(Event(searchException))
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

    //Propagate event exception to UI
    private val _searchExceptionEventLiveData = MutableLiveData<Event<SearchException>>()
    val searchExceptionEvenLiveData: LiveData<Event<SearchException>> = _searchExceptionEventLiveData

    fun submitSearchQuery(movieSearched: String) {
        currentUserSearch = movieSearched

        //Invalidate function will invoke Pager function to get a new paging source
        pagingSource?.invalidate()
    }
}