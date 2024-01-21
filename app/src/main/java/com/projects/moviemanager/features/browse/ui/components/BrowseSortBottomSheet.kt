package com.projects.moviemanager.features.browse.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.SortTypeItem
import com.projects.moviemanager.common.ui.components.SystemNavBarSpacer
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.common.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.components.button.SortButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
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

    val textColor = MaterialTheme.colorScheme.onPrimary

    ModalBottomSheet(
        onDismissRequest = dismissBottomSheet,
        containerColor = MainBarGreyColor
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-SMALL_MARGIN).dp),
            text = stringResource(id = R.string.sort_by_header),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
        Divider(
            color = MaterialTheme.colorScheme.inverseSurface,
            modifier = Modifier.padding(top = SMALL_PADDING.dp)
        )
        when (selectedMediaType) {
            MediaType.MOVIE -> {
                CreateSortButtons(
                    list = movieSortTypeList,
                    selectedIndex = selectedMovieIndex,
                    viewModel = mainViewModel,
                    textColor = textColor,
                    updateIndex = { selectedMovieIndex = it },
                    dismissBottomSheet = dismissBottomSheet
                )
            }
            MediaType.SHOW -> {
                CreateSortButtons(
                    list = showSortTypeList,
                    selectedIndex = selectedShowIndex,
                    viewModel = mainViewModel,
                    textColor = textColor,
                    updateIndex = { selectedShowIndex = it },
                    dismissBottomSheet = dismissBottomSheet
                )
            }
            MediaType.PERSON -> {}
        }
        Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
        SystemNavBarSpacer()
    }
}

@Composable
private fun CreateSortButtons(
    list: List<SortTypeItem>,
    selectedIndex: Int,
    viewModel: MainViewModel,
    textColor: Color,
    updateIndex: (Int) -> Unit,
    dismissBottomSheet: () -> Unit
) {
    list.forEachIndexed { index, sortTypeItem ->
        SortButton(
            isSelected = selectedIndex == index,
            text = stringResource(id = sortTypeItem.titleRes),
            textColor = textColor,
            onClick = {
                viewModel.updateSortType(sortTypeItem)
                updateIndex(index)
                dismissBottomSheet()
            }
        )
    }
}
