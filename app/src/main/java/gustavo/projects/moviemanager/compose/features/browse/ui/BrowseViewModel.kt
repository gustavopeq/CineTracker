package gustavo.projects.moviemanager.compose.features.browse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.compose.common.MediaType
import gustavo.projects.moviemanager.compose.common.ui.components.SortTypeItem
import gustavo.projects.moviemanager.compose.features.browse.domain.BrowseInteractor
import gustavo.projects.moviemanager.compose.features.browse.events.BrowseEvent
import gustavo.projects.moviemanager.domain.models.content.BaseMediaContent
import gustavo.projects.moviemanager.domain.models.util.ContentListType
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val interactor: BrowseInteractor
) : ViewModel() {

    private val _moviePager: MutableStateFlow<PagingData<BaseMediaContent>> = MutableStateFlow(
        PagingData.empty()
    )
    val moviePager: StateFlow<PagingData<BaseMediaContent>> get() = _moviePager

    private var movieSortTypeSelected: ContentListType? = null
    private var showSortTypeSelected: ContentListType? = null

    private val _mediaTypeSelected = MutableStateFlow(MediaType.MOVIE)
    val mediaTypeSelected: StateFlow<MediaType> get() = _mediaTypeSelected

    fun onEvent(event: BrowseEvent) {
        when (event) {
            is BrowseEvent.UpdateSortType -> updateSortType(event.movieListType)
            is BrowseEvent.UpdateMediaType -> updateMediaType(event.mediaType)
        }
    }

    private fun updateSortType(
        sortTypeItem: SortTypeItem
    ) {
        val currentSortType = when (_mediaTypeSelected.value) {
            MediaType.MOVIE -> movieSortTypeSelected
            MediaType.SHOW -> showSortTypeSelected
        }

        if (sortTypeItem.listType != currentSortType) {
            viewModelScope.launch {
                when (_mediaTypeSelected.value) {
                    MediaType.MOVIE -> {
                        movieSortTypeSelected = sortTypeItem.listType
                    }
                    MediaType.SHOW -> {
                        showSortTypeSelected = sortTypeItem.listType
                    }
                }

                interactor.getMediaContentListPager(
                    sortTypeItem.listType,
                    _mediaTypeSelected.value
                )
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .collect {
                        _moviePager.value = it
                    }
            }
        }
    }

    private fun updateMediaType(
        mediaType: MediaType
    ) {
        _mediaTypeSelected.value = mediaType
    }
}
