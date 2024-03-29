package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.projects.moviemanager.common.ui.screen.ErrorScreen
import com.projects.moviemanager.features.browse.BrowseScreen
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.features.home.ui.Home
import com.projects.moviemanager.features.watchlist.WatchlistScreen
import com.projects.moviemanager.navigation.ScreenUI
import com.projects.moviemanager.navigation.components.navigateToTopLevelDestination

class HomeScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = currentBackStackEntry?.destination?.route

        Home(
            goToDetails = { contentId, mediaType ->
                navController.navigate(
                    DetailsScreen.routeWithArguments(contentId, mediaType.name)
                )
            },
            goToWatchlist = {
                navigateToTopLevelDestination(
                    navController = navController,
                    destination = WatchlistScreen.route()
                )
            },
            goToBrowse = {
                navigateToTopLevelDestination(
                    navController = navController,
                    destination = BrowseScreen.route()
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
