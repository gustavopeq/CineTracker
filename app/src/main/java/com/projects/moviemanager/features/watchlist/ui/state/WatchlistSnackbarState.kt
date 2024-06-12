package com.projects.moviemanager.features.watchlist.ui.state

import com.projects.moviemanager.common.domain.models.util.SnackbarState
import com.projects.moviemanager.features.watchlist.model.WatchlistItemAction

data class WatchlistSnackbarState(
    val listId: Int? = null,
    val itemAction: WatchlistItemAction = WatchlistItemAction.ITEM_REMOVED
) : SnackbarState()
