package com.projects.moviemanager.features.details.ui.state

import com.projects.moviemanager.common.domain.models.util.SnackbarState
import com.projects.moviemanager.features.watchlist.model.DefaultLists

data class DetailsSnackbarState(
    val listId: Int = DefaultLists.WATCHLIST.listId,
    val addedItem: Boolean = false
) : SnackbarState()
