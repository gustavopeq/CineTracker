package com.projects.moviemanager.features.watchlist.model

import com.projects.moviemanager.R
import com.projects.moviemanager.common.util.capitalized

enum class DefaultLists(val listId: Int) {
    WATCHLIST(1),
    WATCHED(2),
    ADD_NEW(3);

    override fun toString(): String {
        return super.toString().lowercase().capitalized()
    }
    companion object {
        fun getListById(listId: Int): DefaultLists? {
            return values().firstOrNull { it.listId == listId }
        }
        fun getOtherList(listId: Int): DefaultLists {
            return when (listId) {
                WATCHLIST.listId -> WATCHED
                else -> WATCHLIST
            }
        }

        fun getListLocalizedName(
            list: DefaultLists?
        ): Int {
            return when (list) {
                WATCHLIST -> R.string.watchlist_tab
                WATCHED -> R.string.watched_tab
                else -> R.string.unknown
            }
        }
    }
}
