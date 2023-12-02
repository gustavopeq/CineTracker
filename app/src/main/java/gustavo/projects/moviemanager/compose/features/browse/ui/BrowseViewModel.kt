package gustavo.projects.moviemanager.compose.features.browse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.compose.features.browse.domain.BrowseInteractor
import gustavo.projects.moviemanager.compose.features.browse.events.BrowseEvent
import gustavo.projects.moviemanager.domain.models.movie.Movie
import gustavo.projects.moviemanager.domain.models.util.MovieListType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val interactor: BrowseInteractor
) : ViewModel() {

    private val _moviePager: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(
        PagingData.empty()
    )
    val moviePager: StateFlow<PagingData<Movie>> get() = _moviePager

    private var currentSortType: MovieListType? = null

    fun onEvent(event: BrowseEvent) {
        when (event) {
            is BrowseEvent.UpdateSortType -> updateSortType(event.movieListType)
        }
    }

    private fun updateSortType(
        movieListType: MovieListType
    ) {
        if (movieListType != currentSortType) {
            viewModelScope.launch {
                currentSortType = movieListType
                interactor.getMovieListPager(movieListType)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .collect {
                        _moviePager.value = it
                    }
            }
        }
    }
}
