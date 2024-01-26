package com.projects.moviemanager.features.search.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.features.search.domain.SearchInteractor
import com.projects.moviemanager.features.search.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: MutableState<String> get() = _searchQuery

    private val _searchResults = MutableStateFlow(emptyList<GenericSearchContent>())
    val searchResult: StateFlow<List<GenericSearchContent>> get() = _searchResults

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClearSearchBar -> onClearSearchBar()
            is SearchEvent.SearchQuery -> onSearchQuery(event.query)
            is SearchEvent.FilterTypeSelected -> onFilterTypeSelected(event.mediaType)
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
                _searchResults.value = interactor.onSearchQuery(
                    query = query,
                    page = 1,
                    mediaType = null
                )
            }
        }
    }

    private fun onFilterTypeSelected(
        mediaType: MediaType?
    ) {
        if (_searchQuery.value.isNotEmpty()) {
            viewModelScope.launch {
                _searchResults.value = interactor.onSearchQuery(
                    query = _searchQuery.value,
                    page = 1,
                    mediaType = mediaType
                )
            }
        }
    }
}
