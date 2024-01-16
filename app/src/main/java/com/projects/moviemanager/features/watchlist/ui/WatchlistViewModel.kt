package com.projects.moviemanager.features.watchlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistInteractor: WatchlistInteractor
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            watchlistInteractor.getAllItems()
        }
    }
}
