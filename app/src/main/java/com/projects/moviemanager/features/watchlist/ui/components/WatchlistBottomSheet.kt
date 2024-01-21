package com.projects.moviemanager.features.watchlist.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.SystemNavBarSpacer
import com.projects.moviemanager.common.ui.components.button.SortButton
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.util.Constants.UNSELECTED_OPTION_INDEX

@OptIn(ExperimentalMaterial3Api::class)
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

    val onSortButtonClick: (Boolean, Int, WatchlistSortTypeItem) -> Unit = { isButtonSelected, index, sortItem ->
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

    val textColor = MaterialTheme.colorScheme.onPrimary

    ModalBottomSheet(
        onDismissRequest = { dismissBottomSheet() },
        containerColor = MainBarGreyColor
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-UiConstants.SMALL_MARGIN).dp),
            text = stringResource(id = R.string.sort_by_header),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
        Divider(
            color = MaterialTheme.colorScheme.inverseSurface,
            modifier = Modifier.padding(top = UiConstants.SMALL_PADDING.dp)
        )
        sortOptions.forEachIndexed { index, sortItem ->
            val isButtonSelected = index == selectedIndex
            SortButton(
                text = stringResource(
                    id = R.string.watchlist_sort_button,
                    stringResource(id = sortItem.titleRes)
                ),
                textColor = textColor,
                isSelected = isButtonSelected,
                onClick = {
                    onSortButtonClick(isButtonSelected, index, sortItem)
                }
            )
        }
        Spacer(modifier = Modifier.height(UiConstants.SMALL_PADDING.dp))
        SystemNavBarSpacer()
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
