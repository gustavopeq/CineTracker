package gustavo.projects.moviemanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import gustavo.projects.carmanager.theme.MovieManagerTheme
import gustavo.projects.moviemanager.compose.navigation.MainNavGraph
import gustavo.projects.moviemanager.compose.navigation.components.MainNavBar
import gustavo.projects.moviemanager.compose.navigation.components.MainNavBarItem

@Composable
fun MainApp() {
    MovieManagerTheme {
        val navController = rememberNavController()
        val navItems = mainNavBarItems

        Scaffold(
            bottomBar = {
                MainNavBar(navController = navController, navBarItems = navItems)
            },
            content = {
                Box(modifier = Modifier.padding(it)) {
                    MainNavGraph(navController)
                }
            }
        )
    }
}

private val mainNavBarItems = listOf<MainNavBarItem>(
    MainNavBarItem.Home,
    MainNavBarItem.Browse,
    MainNavBarItem.Watchlist,
    MainNavBarItem.Search
)
