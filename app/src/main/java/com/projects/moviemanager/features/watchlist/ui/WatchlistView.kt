package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.MovieDetailsInfo
import com.projects.moviemanager.domain.models.content.ShowDetailsInfo
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistCard
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
        WatchlistBody(watchlist = watchlist)
    }
}

@Composable
private fun WatchlistBody(
    watchlist: List<DetailedMediaInfo>
) {
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_MARGIN.dp)
    ) {
        items(watchlist) { mediaInfo ->
            val rating = when (mediaInfo) {
                is MovieDetailsInfo -> mediaInfo.voteAverage
                is ShowDetailsInfo -> mediaInfo.voteAverage
                else -> 0.0
            }

            WatchlistCard(
                title = mediaInfo.title,
                rating = rating ?: 0.0,
                posterUrl = mediaInfo.poster_path,
                mediaType = mediaInfo.mediaType,
                onCardClick = { },
                onRemoveClick = { }
            )
            Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
        }
    }
}
