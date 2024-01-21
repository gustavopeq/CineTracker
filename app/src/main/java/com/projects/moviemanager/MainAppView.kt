package com.projects.moviemanager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.bottomsheet.ModalComponents
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.navigation.MainNavGraph
import com.projects.moviemanager.navigation.components.MainNavBar
import com.projects.moviemanager.navigation.components.MainNavBarItem
import com.projects.moviemanager.navigation.components.TopNavBar
import com.projects.moviemanager.common.ui.theme.MovieManagerTheme

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

        var topBarState by rememberSaveable { mutableStateOf(true) }
        var mainBarState by rememberSaveable { mutableStateOf(true) }

        LaunchedEffect(currentScreen) {
            topBarState = !standaloneScreens.contains(currentScreen)
            mainBarState = !standaloneScreens.contains(currentScreen)
        }

        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = topBarState,
                    enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)),
                    exit = fadeOut(spring(stiffness = Spring.StiffnessHigh))
                ) {
                    TopNavBar(
                        currentScreen = currentScreen,
                        displaySortScreen = displaySortScreen
                    )
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = mainBarState,
                    enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)),
                    exit = fadeOut(spring(stiffness = Spring.StiffnessHigh))
                ) {
                    MainNavBar(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        navBarItems = navItems
                    )
                }
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

val standaloneScreens = listOf(
    DetailsScreen.route()
)
