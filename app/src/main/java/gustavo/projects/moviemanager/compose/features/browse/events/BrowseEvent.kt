package gustavo.projects.moviemanager.compose.features.browse.events

import gustavo.projects.moviemanager.compose.common.MediaType
import gustavo.projects.moviemanager.compose.common.ui.components.SortTypeItem

sealed class BrowseEvent {
    data class UpdateSortType(
        val movieListType: SortTypeItem
    ) : BrowseEvent()
    data class UpdateMediaType(
        val mediaType: MediaType
    ) : BrowseEvent()
}
