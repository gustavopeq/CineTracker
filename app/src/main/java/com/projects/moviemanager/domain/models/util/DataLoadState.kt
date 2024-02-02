package com.projects.moviemanager.domain.models.util

sealed class DataLoadState {
    data object Empty : DataLoadState()
    data object Loading : DataLoadState()
    data object Success : DataLoadState()
    data object Failed : DataLoadState()
}
