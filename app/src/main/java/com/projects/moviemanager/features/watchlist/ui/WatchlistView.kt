package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.GridContentList
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.TabItem
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.MovieDetailsInfo
import com.projects.moviemanager.domain.models.content.ShowDetailsInfo
import com.projects.moviemanager.features.details.ui.components.moreoptions.MoreOptionsTabItem
import com.projects.moviemanager.features.details.ui.components.moreoptions.VideoList
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistCard
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistTabItem

@Composable
fun Watchlist(
    goToDetails: (Int, MediaType) -> Unit
) {
    Watchlist(
        viewModel = hiltViewModel(),
        goToDetails = goToDetails
    )
}

@Composable
private fun Watchlist(
    viewModel: WatchlistViewModel,
    goToDetails: (Int, MediaType) -> Unit
) {
    val watchlist by viewModel.watchlist.collectAsState()
    val watchedList by viewModel.watchedList.collectAsState()

    val availableTabs = listOf(
        WatchlistTabItem.WatchlistTab,
        WatchlistTabItem.WatchedTab
    )

    val (tabList, selectedTabIndex, updateSelectedTab) = setupTabs(
        tabList = availableTabs,
        onTabSelected = { index ->
            viewModel.onEvent(
                WatchlistEvent.SelectList(availableTabs[index].listId)
            )
        }
    )

    val removeItem: (Int, MediaType) -> Unit = { contentId, mediaType ->
        viewModel.onEvent(
            WatchlistEvent.RemoveItem(contentId, mediaType)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GenericTabRow(selectedTabIndex.value, tabList, updateSelectedTab)
        when (tabList[selectedTabIndex.value].tabIndex) {
            WatchlistTabItem.WatchlistTab.tabIndex -> {
                WatchlistBody(
                    watchlist = watchlist,
                    goToDetails = goToDetails,
                    removeItem = removeItem
                )
            }

            WatchlistTabItem.WatchedTab.tabIndex -> {
                WatchlistBody(
                    watchlist = watchedList,
                    goToDetails = goToDetails,
                    removeItem = removeItem
                )
            }
        }
    }
}

@Composable
private fun WatchlistBody(
    watchlist: List<DetailedMediaInfo>,
    goToDetails: (Int, MediaType) -> Unit,
    removeItem: (Int, MediaType) -> Unit
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
                onCardClick = {
                    goToDetails(mediaInfo.id, mediaInfo.mediaType)
                },
                onRemoveClick = {
                    removeItem(mediaInfo.id, mediaInfo.mediaType)
                }
            )
            Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
        }
    }
}
