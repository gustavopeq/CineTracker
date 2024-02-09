package com.projects.moviemanager.common.ui.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.util.UiConstants.BROWSE_SORT_ICON_SIZE
import com.projects.moviemanager.features.watchlist.WatchlistScreen

@Composable
fun SortIconButton(
    mainViewModel: MainViewModel,
    currentScreen: String,
    displaySortScreen: (Boolean) -> Unit
) {
    val watchlistSortSelected by mainViewModel.watchlistSort.collectAsState()
    val iconColor = if (currentScreen == WatchlistScreen.route() && watchlistSortSelected != null) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }
    IconButton(
        onClick = { displaySortScreen(true) }
    ) {
        Icon(
            modifier = Modifier.size(BROWSE_SORT_ICON_SIZE.dp),
            painter = painterResource(id = R.drawable.ic_sort),
            tint = iconColor,
            contentDescription = null
        )
    }
}
