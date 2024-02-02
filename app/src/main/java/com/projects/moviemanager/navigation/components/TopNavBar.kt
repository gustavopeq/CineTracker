package com.projects.moviemanager.navigation.components

import android.view.animation.Animation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.button.SortIconButton
import com.projects.moviemanager.features.browse.BrowseScreen
import com.projects.moviemanager.features.home.HomeScreen
import com.projects.moviemanager.features.home.ui.components.HomeLogoAnimation
import com.projects.moviemanager.features.search.SearchScreen
import com.projects.moviemanager.features.watchlist.WatchlistScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    currentScreen: String?,
    mainViewModel: MainViewModel,
    displaySortScreen: (Boolean) -> Unit
) {
    val title = currentScreen.getScreenNameRes()?.let { stringResource(id = it) }
    val showTopBar = screensWithTopBar.contains(currentScreen)

    if (showTopBar) {
        TopAppBar(
            navigationIcon = {
                AnimatedVisibility(
                    visible = currentScreen == HomeScreen.route(),
                    enter = fadeIn(),
                    exit = ExitTransition.None
                ) {
                    HomeLogoAnimation()
                }
            },
            title = {
                Text(
                    text = title.orEmpty(),
                    style = MaterialTheme.typography.headlineLarge
                )
            },
            actions = {
                if (screensWithSortIcon.contains(currentScreen)) {
                    SortIconButton(
                        mainViewModel = mainViewModel,
                        currentScreen = currentScreen.orEmpty(),
                        displaySortScreen = displaySortScreen
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

private fun String?.getScreenNameRes(): Int? {
    return when (this) {
        HomeScreen.route() -> MainNavBarItem.Home.labelResId
        BrowseScreen.route() -> MainNavBarItem.Browse.labelResId
        WatchlistScreen.route() -> MainNavBarItem.Watchlist.labelResId
        SearchScreen.route() -> MainNavBarItem.Search.labelResId
        else -> null
    }
}

private val screensWithTopBar = listOf(
    HomeScreen.route(),
    BrowseScreen.route(),
    WatchlistScreen.route()
)

private val screensWithSortIcon = listOf(
    BrowseScreen.route(),
    WatchlistScreen.route()
)
