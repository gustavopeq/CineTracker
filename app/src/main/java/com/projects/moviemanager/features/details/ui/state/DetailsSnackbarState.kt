package com.projects.moviemanager.features.details.ui.state

import com.projects.moviemanager.common.domain.models.util.SnackbarState

data class DetailsSnackbarState(
    val listId: String = "",
    val addedItem: Boolean = false
) : SnackbarState()
