package com.projects.moviemanager.navigation

import androidx.navigation.NamedNavArgument

interface Screen {
    fun route(): String
    val arguments: List<NamedNavArgument>
        get() = emptyList()
}