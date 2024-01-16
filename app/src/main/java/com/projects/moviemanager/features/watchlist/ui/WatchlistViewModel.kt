package com.projects.moviemanager.features.watchlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.database.model.ContentEntity
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistInteractor: WatchlistInteractor
) : ViewModel() {

    private val _watchlist = MutableStateFlow(listOf<ContentEntity>())
    val watchlist: StateFlow<List<ContentEntity>> get() = _watchlist

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _watchlist.value = watchlistInteractor.getAllItems()
        }
    }
}
