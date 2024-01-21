package com.projects.moviemanager.features.watchlist.events

import com.projects.moviemanager.common.domain.MediaType

sealed class WatchlistEvent {
    data object LoadWatchlistData : WatchlistEvent()
    data class RemoveItem(
        val contentId: Int,
        val mediaType: MediaType
    ) : WatchlistEvent()
    data class SelectList(
        val list: String
    ) : WatchlistEvent()
    data class UpdateSortType(
        val mediaType: MediaType?
    ) : WatchlistEvent()
}
