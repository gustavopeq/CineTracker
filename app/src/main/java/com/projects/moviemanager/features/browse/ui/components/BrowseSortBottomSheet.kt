package com.projects.moviemanager.features.browse.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.domain.SortTypeItem
import com.projects.moviemanager.common.ui.components.bottomsheet.GenericBottomSheet
import com.projects.moviemanager.common.ui.components.bottomsheet.SortButton

@Composable
fun BrowseSortBottomSheet(
    mainViewModel: MainViewModel,
    selectedMovieSortType: SortTypeItem,
    selectedShowSortType: SortTypeItem,
    selectedMediaType: MediaType,
    displaySortScreen: (Boolean) -> Unit
) {
    val movieSortTypeList = listOf(
        SortTypeItem.NowPlaying,
        SortTypeItem.Popular,
        SortTypeItem.TopRated,
        SortTypeItem.Upcoming
    )

    val showSortTypeList = listOf(
        SortTypeItem.AiringToday,
        SortTypeItem.ShowPopular,
        SortTypeItem.ShowTopRated,
        SortTypeItem.OnTheAir
    )

    var selectedMovieIndex by remember { mutableIntStateOf(selectedMovieSortType.itemIndex) }
    var selectedShowIndex by remember { mutableIntStateOf(selectedShowSortType.itemIndex) }

    val dismissBottomSheet: () -> Unit = {
        displaySortScreen(false)
    }

    BackHandler { dismissBottomSheet() }

    GenericBottomSheet(
        dismissBottomSheet = dismissBottomSheet
    ) {
        when (selectedMediaType) {
            MediaType.MOVIE -> {
                CreateBrowseSortButtons(
                    list = movieSortTypeList,
                    selectedIndex = selectedMovieIndex,
                    viewModel = mainViewModel,
                    updateIndex = { selectedMovieIndex = it },
                    dismissBottomSheet = dismissBottomSheet
                )
            }
            MediaType.SHOW -> {
                CreateBrowseSortButtons(
                    list = showSortTypeList,
                    selectedIndex = selectedShowIndex,
                    viewModel = mainViewModel,
                    updateIndex = { selectedShowIndex = it },
                    dismissBottomSheet = dismissBottomSheet
                )
            }
            else -> {}
        }
    }
}

@Composable
private fun CreateBrowseSortButtons(
    list: List<SortTypeItem>,
    selectedIndex: Int,
    viewModel: MainViewModel,
    updateIndex: (Int) -> Unit,
    dismissBottomSheet: () -> Unit
) {
    list.forEachIndexed { index, sortTypeItem ->
        SortButton(
            isSelected = selectedIndex == index,
            text = stringResource(id = sortTypeItem.titleRes),
            textColor = MaterialTheme.colorScheme.onPrimary,
            onClick = {
                viewModel.updateSortType(sortTypeItem)
                updateIndex(index)
                dismissBottomSheet()
            }
        )
    }
}
