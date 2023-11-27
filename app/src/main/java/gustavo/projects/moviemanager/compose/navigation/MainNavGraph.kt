package gustavo.projects.moviemanager.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gustavo.projects.moviemanager.compose.features.browse.BrowseScreen
import gustavo.projects.moviemanager.compose.features.home.HomeScreen
import gustavo.projects.moviemanager.compose.features.search.SearchScreen
import gustavo.projects.moviemanager.compose.features.watchlist.WatchlistScreen
import gustavo.projects.moviemanager.compose.navigation.screens.BrowseScreenUI
import gustavo.projects.moviemanager.compose.navigation.screens.HomeScreenUI
import gustavo.projects.moviemanager.compose.navigation.screens.SearchScreenUI
import gustavo.projects.moviemanager.compose.navigation.screens.WatchlistScreenUI

private val mainNavDestinations: Map<Screen, ScreenUI> = mapOf(
    HomeScreen to HomeScreenUI(),
    BrowseScreen to BrowseScreenUI(),
    WatchlistScreen to WatchlistScreenUI(),
    SearchScreen to SearchScreenUI()
)

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen.route()
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
