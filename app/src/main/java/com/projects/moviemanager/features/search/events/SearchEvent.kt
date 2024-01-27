package com.projects.moviemanager.features.search.events

import com.projects.moviemanager.features.search.ui.components.SearchTypeFilterItem

sealed class SearchEvent {
    data object ClearSearchBar : SearchEvent()
    data class SearchQuery(
        val query: String
    ) : SearchEvent()
    data class FilterTypeSelected(
        val searchFilter: SearchTypeFilterItem
    ) : SearchEvent()
}
