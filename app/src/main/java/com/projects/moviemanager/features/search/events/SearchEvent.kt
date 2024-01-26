package com.projects.moviemanager.features.search.events

import com.projects.moviemanager.common.domain.MediaType

sealed class SearchEvent {
    data object ClearSearchBar : SearchEvent()
    data class SearchQuery(
        val query: String
    ) : SearchEvent()
    data class FilterTypeSelected(
        val mediaType: MediaType?
    ) : SearchEvent()
}
