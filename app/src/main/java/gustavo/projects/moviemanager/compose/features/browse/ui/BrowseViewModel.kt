package gustavo.projects.moviemanager.compose.features.browse.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.compose.features.browse.ui.state.BrowseState
import gustavo.projects.moviemanager.domain.mappers.MovieMapper
import gustavo.projects.moviemanager.network.ApiClient
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val apiClient: ApiClient
) : ViewModel() {

    var browseState by mutableStateOf(BrowseState())
        private set

    init {
        viewModelScope.launch {
            fetchNowPlaying()
        }
    }

    private suspend fun fetchNowPlaying() {
        val result = apiClient.getNowPlayingMoviesPage(pageIndex = 1, language = "en-US")
        val listOfMovies = result.body.results.mapNotNull {
            MovieMapper.buildFrom(it)
        }
        browseState.listOfMovies.value = listOfMovies
    }
}
