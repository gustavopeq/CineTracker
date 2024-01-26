package com.projects.moviemanager.features.search.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.features.search.domain.SearchInteractor
import com.projects.moviemanager.features.search.events.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val interactor: SearchInteractor
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: MutableState<String> get() = _searchQuery

    private val _searchResults: MutableStateFlow<PagingData<GenericSearchContent>> =
        MutableStateFlow(PagingData.empty())
    val searchResult: StateFlow<PagingData<GenericSearchContent>> get() = _searchResults

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClearSearchBar -> onClearSearchBar()
            is SearchEvent.SearchQuery -> onSearchQuery(event.query)
            is SearchEvent.FilterTypeSelected -> onFilterTypeSelected(event.mediaType)
        }
    }

    private fun onClearSearchBar() {
        _searchQuery.value = ""

        viewModelScope.launch {
            _searchResults.emit(PagingData.empty())
        }
    }

    private fun onSearchQuery(
        query: String
    ) {
        _searchQuery.value = query
        if (query.isNotEmpty()) {
            search(_searchQuery.value)
        }
    }

    private fun onFilterTypeSelected(
        mediaType: MediaType?
    ) {
        if (_searchQuery.value.isNotEmpty()) {
            search(
                _searchQuery.value,
                mediaType
            )
        }
    }

    private fun search(
        query: String,
        mediaType: MediaType? = null
    ) {
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
