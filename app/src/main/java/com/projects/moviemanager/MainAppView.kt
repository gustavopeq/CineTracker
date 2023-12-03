package com.projects.moviemanager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.projects.carmanager.theme.MovieManagerTheme
import com.projects.moviemanager.compose.common.MainViewModel
import com.projects.moviemanager.compose.common.ui.components.ModalComponents
import com.projects.moviemanager.compose.navigation.MainNavGraph
import com.projects.moviemanager.compose.navigation.components.MainNavBar
import com.projects.moviemanager.compose.navigation.components.MainNavBarItem
import com.projects.moviemanager.compose.navigation.components.TopNavBar

@Composable
fun MainApp() {
    MovieManagerTheme {
        val mainViewModel: MainViewModel = hiltViewModel()
        val navController = rememberNavController()
        val navItems = mainNavBarItems
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = currentBackStackEntry?.destination?.route

        var showSortBottomSheet by remember { mutableStateOf(false) }

        val displaySortScreen: (Boolean) -> Unit = {
            showSortBottomSheet = it
        }

        Scaffold(
            topBar = {
                TopNavBar(
                    currentScreen = currentScreen,
                    displaySortScreen = displaySortScreen
                )
            },
            bottomBar = {
                MainNavBar(
                    navController = navController,
                    navBarItems = navItems
                )
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    MainNavGraph(navController)
                }
            }
        )

        ModalComponents(
            mainViewModel = mainViewModel,
            showSortBottomSheet = showSortBottomSheet,
            displaySortScreen = displaySortScreen
        )
    }
}

val mainNavBarItems = listOf<MainNavBarItem>(
    MainNavBarItem.Home,
    MainNavBarItem.Browse,
    MainNavBarItem.Watchlist,
    MainNavBarItem.Search
)
