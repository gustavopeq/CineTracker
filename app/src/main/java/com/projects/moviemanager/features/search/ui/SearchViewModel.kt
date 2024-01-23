package com.projects.moviemanager.features.search.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.projects.moviemanager.features.search.events.SearchEvent
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: MutableState<String> get() = _searchQuery

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClearSearchBar -> onClearSearchBar()
            is SearchEvent.SearchQuery -> onSearchQuery(event.query)
        }
    }

    private fun onClearSearchBar() {
        _searchQuery.value = ""
    }

    private fun onSearchQuery(
        query: String
    ) {
        _searchQuery.value = query
    }
}
