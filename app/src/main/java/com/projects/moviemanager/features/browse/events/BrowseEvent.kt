package com.projects.moviemanager.features.browse.events

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.SortTypeItem

sealed class BrowseEvent {
    data class UpdateSortType(
        val movieListType: SortTypeItem,
        val mediaType: MediaType
    ) : BrowseEvent()
    data class UpdateMediaType(
        val mediaType: MediaType
    ) : BrowseEvent()
}
