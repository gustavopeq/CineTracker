package com.projects.moviemanager.compose.features.search

import com.projects.moviemanager.compose.navigation.Screen

object SearchScreen : Screen {
    private const val SEARCH_ROUTE = "search"
    override fun route(): String = SEARCH_ROUTE
}