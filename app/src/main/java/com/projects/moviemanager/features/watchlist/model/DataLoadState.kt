package com.projects.moviemanager.features.watchlist.model

sealed class DataLoadState {
    data object Empty : DataLoadState()
    data object Loading : DataLoadState()
    data object Success : DataLoadState()
    data object Failed : DataLoadState()
}
