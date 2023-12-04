package com.projects.moviemanager.compose.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projects.moviemanager.compose.features.browse.BrowseScreen
import com.projects.moviemanager.compose.features.details.DetailsScreen
import com.projects.moviemanager.compose.features.home.HomeScreen
import com.projects.moviemanager.compose.features.search.SearchScreen
import com.projects.moviemanager.compose.features.watchlist.WatchlistScreen
import com.projects.moviemanager.compose.navigation.screens.BrowseScreenUI
import com.projects.moviemanager.compose.navigation.screens.DetailsScreenUI
import com.projects.moviemanager.compose.navigation.screens.HomeScreenUI
import com.projects.moviemanager.compose.navigation.screens.SearchScreenUI
import com.projects.moviemanager.compose.navigation.screens.WatchlistScreenUI

private val mainNavDestinations: Map<Screen, ScreenUI> = mapOf(
    HomeScreen to HomeScreenUI(),
    BrowseScreen to BrowseScreenUI(),
    WatchlistScreen to WatchlistScreenUI(),
    SearchScreen to SearchScreenUI(),
    DetailsScreen to DetailsScreenUI()
)

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen.route(),
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        mainNavDestinations.forEach { (screen, screenUI) ->
            composable(screen.route(), screen.arguments) {
                screenUI.UI(
                    navController = navController,
                    navArguments = it.arguments
                )
            }
        }
    }
}
