package com.projects.moviemanager.compose.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

interface ScreenUI {
    @Composable
    fun UI(navController: NavController, navArguments: Bundle?)
}