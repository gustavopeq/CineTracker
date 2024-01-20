package com.projects.moviemanager.features.watchlist.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.tab.TabItem
import com.projects.moviemanager.features.watchlist.model.DefaultLists

sealed class WatchlistTabItem(
    @StringRes override val tabResId: Int,
    override var tabIndex: Int = -1,
    val listId: String
) : TabItem {
    data object WatchlistTab : WatchlistTabItem(
        tabResId = R.string.watchlist_tab,
        listId = DefaultLists.WATCHLIST.listId
    )
    data object WatchedTab : WatchlistTabItem(
        tabResId = R.string.watched_tab,
        listId = DefaultLists.WATCHED.listId
    )
}
