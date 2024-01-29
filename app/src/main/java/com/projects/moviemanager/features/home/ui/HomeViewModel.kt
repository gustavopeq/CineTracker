package com.projects.moviemanager.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.features.home.domain.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeInteractor: HomeInteractor
) : ViewModel() {
    private val _trendingMulti: MutableStateFlow<List<GenericSearchContent>> = MutableStateFlow(
        emptyList()
    )
    val trendingMulti: StateFlow<List<GenericSearchContent>> get() = _trendingMulti

    private val _myWatchlist: MutableStateFlow<List<GenericSearchContent>> = MutableStateFlow(
        emptyList()
    )
    val myWatchlist: StateFlow<List<GenericSearchContent>> get() = _myWatchlist

    init {
        viewModelScope.launch {
            _trendingMulti.value = homeInteractor.getTrendingMulti()
        }

        viewModelScope.launch(Dispatchers.IO) {
            _myWatchlist.value = homeInteractor.getAllWatchlist()
            Timber.tag("print").d("watchlist: ${_myWatchlist.value}")
        }
    }
}
