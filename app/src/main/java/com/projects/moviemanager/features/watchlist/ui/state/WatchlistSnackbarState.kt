package com.projects.moviemanager.features.watchlist.ui.state

import com.projects.moviemanager.common.domain.models.util.SnackbarState

data class WatchlistSnackbarState(
    val listId: String = ""
) : SnackbarState()
