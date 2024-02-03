package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.projects.moviemanager.common.ui.screen.ErrorScreen
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.features.watchlist.ui.Watchlist
import com.projects.moviemanager.navigation.ScreenUI

class WatchlistScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = currentBackStackEntry?.destination?.route

        Watchlist(
            goToDetails = { contentId, mediaType ->
                navController.navigate(
                    DetailsScreen.routeWithArguments(contentId, mediaType.name)
                )
            },
            goToErrorScreen = {
                if (currentScreen != ErrorScreen.route()) {
                    navController.navigate(ErrorScreen.route())
                }
            }
        )
    }
}
