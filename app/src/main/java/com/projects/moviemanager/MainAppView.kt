package com.projects.moviemanager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.bottomsheet.ModalComponents
import com.projects.moviemanager.common.ui.screen.ErrorScreen
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.theme.MovieManagerTheme
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.features.watchlist.ui.components.CreateListBottomSheet
import com.projects.moviemanager.navigation.MainNavGraph
import com.projects.moviemanager.navigation.components.MainNavBar
import com.projects.moviemanager.navigation.components.MainNavBarItem
import com.projects.moviemanager.navigation.components.TopNavBar

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
        SystemBarsContainer(
            currentScreen = currentScreen
        ) {
            Scaffold(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars),
                topBar = {
                    TopNavBar(
                        currentScreen = currentScreen,
                        mainViewModel = mainViewModel,
                        displaySortScreen = displaySortScreen
                    )
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
        }

        ModalComponents(
            mainViewModel = mainViewModel,
            showSortBottomSheet = showSortBottomSheet,
            displaySortScreen = displaySortScreen
        )

        CreateListBottomSheet(
            mainViewModel = mainViewModel
        )
    }
}

@Composable
fun SystemBarsContainer(
    currentScreen: String? = null,
    appScaffold: @Composable () -> Unit
) {
    val statusBarColor = when (currentScreen) {
        MainNavBarItem.Search.screen.route() -> MainBarGreyColor
        else -> MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                .align(Alignment.TopCenter)
                .background(color = statusBarColor)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                .align(Alignment.BottomCenter)
                .background(color = MainBarGreyColor)
        )

        appScaffold()
    }
}

val mainNavBarItems = listOf<MainNavBarItem>(
    MainNavBarItem.Home,
    MainNavBarItem.Browse,
    MainNavBarItem.Watchlist,
    MainNavBarItem.Search
)

val standaloneScreens = listOf(
    DetailsScreen.route(),
    ErrorScreen.route()
)
