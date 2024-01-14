package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.features.home.ui.Home
import com.projects.moviemanager.navigation.ScreenUI

class HomeScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Home()
    }
}
