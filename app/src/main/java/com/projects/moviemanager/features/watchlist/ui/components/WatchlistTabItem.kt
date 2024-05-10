package com.projects.moviemanager.features.watchlist.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.tab.TabItem
import com.projects.moviemanager.common.util.Constants.ADD_NEW_TAB_INDEX
import com.projects.moviemanager.common.util.Constants.UNSELECTED_OPTION_INDEX
import com.projects.moviemanager.features.watchlist.model.DefaultLists

sealed class WatchlistTabItem(
    @StringRes override val tabResId: Int? = null,
    override val tabName: String? = "",
    override var tabIndex: Int = UNSELECTED_OPTION_INDEX,
    open val listId: Int
) : TabItem {
    data object WatchlistTab : WatchlistTabItem(
        tabResId = R.string.watchlist_tab,
        listId = DefaultLists.WATCHLIST.listId
    )
    data object WatchedTab : WatchlistTabItem(
        tabResId = R.string.watched_tab,
        listId = DefaultLists.WATCHED.listId
    )
    data object AddNewTab : WatchlistTabItem(
        tabResId = R.string.add_new_tab,
        listId = DefaultLists.ADD_NEW.listId,
        tabIndex = ADD_NEW_TAB_INDEX
    )
    data class CustomTab(
        override val tabResId: Int? = null,
        override val tabName: String?,
        override var tabIndex: Int = UNSELECTED_OPTION_INDEX,
        override var listId: Int
    ) : WatchlistTabItem(tabResId, tabName, tabIndex, listId)
}
