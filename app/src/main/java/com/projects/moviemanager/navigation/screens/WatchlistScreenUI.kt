package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.navigation.ScreenUI
import com.projects.moviemanager.features.watchlist.ui.Watchlist

class WatchlistScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Watchlist()
    }
}
