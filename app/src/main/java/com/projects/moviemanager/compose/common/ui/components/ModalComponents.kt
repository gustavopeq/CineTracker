package com.projects.moviemanager.compose.common.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.projects.moviemanager.compose.common.MainViewModel
import com.projects.moviemanager.compose.features.browse.ui.components.SortBottomSheet

@Composable
fun ModalComponents(
    mainViewModel: MainViewModel,
    showSortBottomSheet: Boolean,
    displaySortScreen: (Boolean) -> Unit
) {
    val selectedMovieSortType by mainViewModel.movieSortType.collectAsState()
    val selectedShowSortType by mainViewModel.showSortType.collectAsState()
    val selectedMediaType by mainViewModel.currentMediaTypeSelected.collectAsState()

    if (showSortBottomSheet) {
        SortBottomSheet(
            mainViewModel = mainViewModel,
            selectedMovieSortType = selectedMovieSortType,
            selectedShowSortType = selectedShowSortType,
            selectedMediaType = selectedMediaType,
            displaySortScreen = displaySortScreen
        )
    }
}
