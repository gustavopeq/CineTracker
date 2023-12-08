package com.projects.moviemanager.features.watchlist

import com.projects.moviemanager.navigation.Screen

object WatchlistScreen : Screen {
    private const val WATCHLIST_ROUTE = "watchlist"
    override fun route(): String = WATCHLIST_ROUTE
}