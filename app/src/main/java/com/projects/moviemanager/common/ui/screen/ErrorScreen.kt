package com.projects.moviemanager.common.ui.screen

import com.projects.moviemanager.navigation.Screen

object ErrorScreen : Screen {
    private const val ERROR_ROUTE = "error"
    override fun route(): String = ERROR_ROUTE
}
