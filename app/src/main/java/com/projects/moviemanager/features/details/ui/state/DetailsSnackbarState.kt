package com.projects.moviemanager.features.details.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DetailsSnackbarState(
    val displaySnackbar: MutableState<Boolean> = mutableStateOf(false),
    val listId: String = "",
    val addedItem: Boolean = false
)
