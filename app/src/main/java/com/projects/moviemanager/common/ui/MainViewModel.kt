package com.projects.moviemanager.common.ui

import androidx.lifecycle.ViewModel
import com.projects.moviemanager.common.domain.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import com.projects.moviemanager.common.ui.components.SortTypeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _movieSortType = MutableStateFlow<SortTypeItem>(SortTypeItem.NowPlaying)
    val movieSortType: StateFlow<SortTypeItem> get() = _movieSortType

    private val _showSortType = MutableStateFlow<SortTypeItem>(SortTypeItem.AiringToday)
    val showSortType: StateFlow<SortTypeItem> get() = _showSortType

    private val _currentMediaTypeSelected = MutableStateFlow(MediaType.MOVIE)
    val currentMediaTypeSelected: StateFlow<MediaType> get() = _currentMediaTypeSelected

    fun updateSortType(
        sortTypeItem: SortTypeItem
    ) {
        when (_currentMediaTypeSelected.value) {
            MediaType.MOVIE -> _movieSortType.value = sortTypeItem
            MediaType.SHOW -> _showSortType.value = sortTypeItem
            MediaType.PERSON -> {}
        }
    }

    fun updateMediaType(
        mediaType: MediaType
    ) {
        _currentMediaTypeSelected.value = mediaType
    }
}
