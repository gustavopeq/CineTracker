package com.projects.moviemanager.compose.features.browse.events

import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.compose.common.ui.components.SortTypeItem

sealed class BrowseEvent {
    data class UpdateSortType(
        val movieListType: SortTypeItem,
        val mediaType: MediaType
    ) : BrowseEvent()
    data class UpdateMediaType(
        val mediaType: MediaType
    ) : BrowseEvent()
}
