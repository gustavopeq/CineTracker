package com.projects.moviemanager.features.search.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.features.search.domain.SearchInteractor
import com.projects.moviemanager.features.search.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor
) : ViewModel() {

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
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                interactor.onSearchQuery(
                    query = query,
                    page = 1
                )
            }
        }
    }
}
