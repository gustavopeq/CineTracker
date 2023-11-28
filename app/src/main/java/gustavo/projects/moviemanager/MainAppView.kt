package gustavo.projects.moviemanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gustavo.projects.carmanager.theme.MovieManagerTheme
import gustavo.projects.moviemanager.compose.navigation.MainNavGraph
import gustavo.projects.moviemanager.compose.navigation.components.MainNavBar
import gustavo.projects.moviemanager.compose.navigation.components.MainNavBarItem
import gustavo.projects.moviemanager.compose.navigation.components.TopNavBar

@Composable
fun MainApp() {
    MovieManagerTheme {
        val navController = rememberNavController()
        val navItems = mainNavBarItems
        val context = LocalContext.current
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = currentBackStackEntry?.destination?.route
        var screenTitle by remember {
            mutableStateOf(context.resources.getString(R.string.main_nav_home))
        }

        val updateScreenTitle: (Int) -> Unit = {
            val title = context.resources.getString(it)
            screenTitle = title
        }

        Scaffold(
            topBar = {
                TopNavBar(
                    currentScreen = currentScreen,
                    screenTitle = screenTitle
                )
            },
            bottomBar = {
                MainNavBar(
                    navController = navController,
                    navBarItems = navItems,
                    screenTitle = updateScreenTitle
                )
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
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
