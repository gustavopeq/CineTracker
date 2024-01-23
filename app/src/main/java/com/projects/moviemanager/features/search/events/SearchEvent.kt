package com.projects.moviemanager.features.search.events

sealed class SearchEvent {
    data object ClearSearchBar : SearchEvent()
    data class SearchQuery(
        val query: String
    ) : SearchEvent()
}
