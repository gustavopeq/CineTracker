package com.projects.moviemanager.features.details.ui.events

sealed class DetailsEvents {
    data object FetchDetails : DetailsEvents()
    data object OnError : DetailsEvents()
    data class ToggleContentFromList(
        val listId: String
    ) : DetailsEvents()
}
