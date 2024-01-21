package com.projects.moviemanager.common.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.features.browse.BrowseScreen
import com.projects.moviemanager.features.browse.ui.components.SortBottomSheet
import com.projects.moviemanager.features.watchlist.WatchlistScreen
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistSortBottomSheet

@Composable
fun ModalComponents(
    mainViewModel: MainViewModel,
    showSortBottomSheet: Boolean,
    displaySortScreen: (Boolean) -> Unit
) {
    val selectedMovieSortType by mainViewModel.movieSortType.collectAsState()
    val selectedShowSortType by mainViewModel.showSortType.collectAsState()
    val selectedMediaType by mainViewModel.currentMediaTypeSelected.collectAsState()
    val currentScreen by mainViewModel.currentScreen.collectAsState()

    if (showSortBottomSheet) {
        when (currentScreen) {
            BrowseScreen.route() -> {
                SortBottomSheet(
                    mainViewModel = mainViewModel,
                    selectedMovieSortType = selectedMovieSortType,
                    selectedShowSortType = selectedShowSortType,
                    selectedMediaType = selectedMediaType,
                    displaySortScreen = displaySortScreen
                )
            }
            WatchlistScreen.route() -> {
                WatchlistSortBottomSheet(
                    mainViewModel = mainViewModel,
                    displaySortScreen = displaySortScreen
                )
            }
        }
    }
}
