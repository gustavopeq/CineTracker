package com.projects.moviemanager.features.watchlist.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.bottomsheet.GenericBottomSheet
import com.projects.moviemanager.common.ui.components.bottomsheet.SortButton
import com.projects.moviemanager.util.Constants.UNSELECTED_OPTION_INDEX

@Composable
fun WatchlistSortBottomSheet(
    mainViewModel: MainViewModel,
    displaySortScreen: (Boolean) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(UNSELECTED_OPTION_INDEX) }
    val sortOptions = listOf(
        WatchlistSortTypeItem.MovieOnly,
        WatchlistSortTypeItem.ShowOnly
    )

    val dismissBottomSheet: () -> Unit = {
        displaySortScreen(false)
    }

    val onSortButtonClick: (Boolean, Int, WatchlistSortTypeItem) -> Unit = {
            isButtonSelected, index, sortItem ->
        selectedIndex = if (isButtonSelected) {
            UNSELECTED_OPTION_INDEX
        } else {
            index
        }
        val sortMediaType = selectSortMediaType(
            selectedIndex = selectedIndex,
            sortTypeItem = sortItem
        )
        mainViewModel.updateWatchlistSort(sortMediaType)
    }

    BackHandler { dismissBottomSheet() }

    GenericBottomSheet(
        dismissBottomSheet = dismissBottomSheet
    ) {
        sortOptions.forEachIndexed { index, sortItem ->
            val isButtonSelected = index == selectedIndex
            SortButton(
                text = stringResource(
                    id = R.string.watchlist_sort_button,
                    stringResource(id = sortItem.titleRes)
                ),
                textColor = MaterialTheme.colorScheme.onPrimary,
                isSelected = isButtonSelected,
                onClick = {
                    onSortButtonClick(isButtonSelected, index, sortItem)
                }
            )
        }
    }
}

fun selectSortMediaType(
    selectedIndex: Int,
    sortTypeItem: WatchlistSortTypeItem
): MediaType? {
    return if (selectedIndex != UNSELECTED_OPTION_INDEX) {
        when (sortTypeItem) {
            is WatchlistSortTypeItem.MovieOnly -> MediaType.MOVIE
            is WatchlistSortTypeItem.ShowOnly -> MediaType.SHOW
        }
    } else {
        null
    }
}
