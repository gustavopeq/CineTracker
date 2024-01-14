package com.projects.moviemanager.navigation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.features.browse.BrowseScreen
import com.projects.moviemanager.features.home.HomeScreen
import com.projects.moviemanager.features.search.SearchScreen
import com.projects.moviemanager.features.watchlist.WatchlistScreen
import com.projects.moviemanager.navigation.Screen

sealed class MainNavBarItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @DrawableRes val iconResId: Int
) {
    data object Home : MainNavBarItem(
        screen = HomeScreen,
        labelResId = R.string.main_nav_home,
        iconResId = R.drawable.ic_home
    )
    data object Browse : MainNavBarItem(
        screen = BrowseScreen,
        labelResId = R.string.main_nav_browse,
        iconResId = R.drawable.ic_browse
    )
    data object Watchlist : MainNavBarItem(
        screen = WatchlistScreen,
        labelResId = R.string.main_nav_watchlist,
        iconResId = R.drawable.ic_watchlist
    )
    data object Search : MainNavBarItem(
        screen = SearchScreen,
        labelResId = R.string.main_nav_search,
        iconResId = R.drawable.ic_search
    )
}
