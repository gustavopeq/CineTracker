package gustavo.projects.moviemanager.compose.features.browse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.compose.features.browse.domain.BrowseInteractor
import gustavo.projects.moviemanager.domain.models.util.MovieListType
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val interactor: BrowseInteractor
) : ViewModel() {

    val moviePager = interactor.getMovieListPager(MovieListType.NOW_PLAYING).cachedIn(
        viewModelScope
    )
}
