package com.projects.moviemanager.features.watchlist.model

enum class DefaultLists(val listId: String) {
    WATCHLIST("watchlist"),
    WATCHED("watched");

    companion object {
        fun getOtherList(listId: String): DefaultLists {
            return when (listId) {
                WATCHLIST.listId -> WATCHED
                else -> WATCHLIST
            }
        }
    }
}
