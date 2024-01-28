package com.projects.moviemanager.features.search.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.util.UiConstants.SEARCH_DEBOUNCE_TIME_MS
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.features.search.domain.SearchInteractor
import com.projects.moviemanager.features.search.events.SearchEvent
import com.projects.moviemanager.features.search.ui.components.SearchTypeFilterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: MutableState<String> get() = _searchQuery

    private val _searchResults: MutableStateFlow<PagingData<GenericSearchContent>> =
        MutableStateFlow(PagingData.empty())
    val searchResult: StateFlow<PagingData<GenericSearchContent>> get() = _searchResults

    private val _searchFilterSelected: MutableState<SearchTypeFilterItem> = mutableStateOf(
        SearchTypeFilterItem.TopResults
    )
    val searchFilterSelected: MutableState<SearchTypeFilterItem> get() = _searchFilterSelected

    private var searchDebounceJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClearSearchBar -> onClearSearchBar()
            is SearchEvent.SearchQuery -> onStartDebounceSearch(event.query)
            is SearchEvent.FilterTypeSelected -> onFilterTypeSelected(event.searchFilter)
        }
    }

    private fun onClearSearchBar() {
        _searchQuery.value = ""
        searchDebounceJob?.cancel()

        viewModelScope.launch {
            _searchResults.emit(PagingData.empty())
        }
    }

    private fun onStartDebounceSearch(
        query: String
    ) {
        if (query.isEmpty()) {
            onClearSearchBar()
            return
        }

        _searchQuery.value = query

        searchDebounceJob?.cancel()

        searchDebounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_TIME_MS)

            if (_searchQuery.value == query) {
                onSearchQuery(query)
            }
        }
    }

    private fun onSearchQuery(
        query: String,
        mediaType: MediaType? = null
    ) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                interactor.onSearchQuery(
                    query = query,
                    mediaType = mediaType
                )
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .collect {
                        _searchResults.value = it
                    }
            }
        }
    }

    private fun onFilterTypeSelected(
        searchFilter: SearchTypeFilterItem
    ) {
        _searchFilterSelected.value = searchFilter
        onSearchQuery(
            query = _searchQuery.value,
            mediaType = searchFilter.mediaType
        )
    }

    override fun onCleared() {
        super.onCleared()
        searchDebounceJob?.cancel()
        searchDebounceJob = null
    }
}
