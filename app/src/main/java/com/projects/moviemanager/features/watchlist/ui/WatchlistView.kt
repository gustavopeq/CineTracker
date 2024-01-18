package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistTabItem

@Composable
fun Watchlist() {
    Watchlist(
        viewModel = hiltViewModel()
    )
}

@Composable
private fun Watchlist(
    viewModel: WatchlistViewModel
) {
    val watchlist by viewModel.watchlist.collectAsState()

    val availableTabs = listOf(
        WatchlistTabItem.WatchlistTab,
        WatchlistTabItem.WatchedTab
    )

    val (tabList, selectedTabIndex, updateSelectedTab) = setupTabs(availableTabs)

    Column(modifier = Modifier.fillMaxSize()) {
        GenericTabRow(selectedTabIndex.value, tabList, updateSelectedTab)
        watchlist.forEach {
            Text(text = it.contentId.toString())
            Text(text = it.mediaType)
        }
    }
}
