package com.projects.moviemanager.features.watchlist.events

import com.projects.moviemanager.common.domain.models.util.MediaType

sealed class WatchlistEvent {
    data object LoadWatchlistData : WatchlistEvent()
    data object OnSnackbarDismiss : WatchlistEvent()
    data object UndoItemAction : WatchlistEvent()
    data class RemoveItem(
        val contentId: Int,
        val mediaType: MediaType
    ) : WatchlistEvent()
    data class SelectList(
        val list: Int
    ) : WatchlistEvent()
    data class UpdateSortType(
        val mediaType: MediaType?
    ) : WatchlistEvent()
    data class UpdateItemListId(
        val contentId: Int,
        val mediaType: MediaType
    ) : WatchlistEvent()
}
