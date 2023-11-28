package gustavo.projects.moviemanager.compose.features.browse.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import gustavo.projects.moviemanager.domain.models.Movie

data class BrowseState(
    var listOfMovies: MutableState<List<Movie>> = mutableStateOf(mutableListOf())
)
