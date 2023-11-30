package gustavo.projects.moviemanager.compose.features.browse.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.compose.features.browse.domain.BrowseInteractor
import gustavo.projects.moviemanager.compose.features.browse.ui.state.BrowseState
import gustavo.projects.moviemanager.domain.models.util.MovieListType
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val interactor: BrowseInteractor
) : ViewModel() {

    var browseState by mutableStateOf(BrowseState())
        private set

    init {
        viewModelScope.launch {
            fetchNowPlaying()
        }
    }

    private suspend fun fetchNowPlaying() {
        val result = interactor.getMovieList(MovieListType.NOW_PLAYING, 1)
        browseState.listOfMovies.value = result
    }
}
