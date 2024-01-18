package com.projects.moviemanager.features.watchlist.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.tab.TabItem

sealed class WatchlistTabItem(
    @StringRes override val tabResId: Int,
    override var tabIndex: Int = -1
) : TabItem {
    data object WatchlistTab : WatchlistTabItem(
        tabResId = R.string.watchlist_tab
    )
    data object WatchedTab : WatchlistTabItem(
        tabResId = R.string.watched_tab
    )
}
