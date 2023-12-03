package com.projects.moviemanager.compose.features.home

import com.projects.moviemanager.compose.navigation.Screen

object HomeScreen : Screen {
    private const val HOME_ROUTE = "home"
    override fun route(): String = HOME_ROUTE
}