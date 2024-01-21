package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.ComponentPlaceholder
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.common.ui.theme.PrimaryYellowColor_90
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.common.ui.util.UiConstants.CARD_ROUND_CORNER
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.WATCHLIST_IMAGE_WIDTH
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.MovieDetailsInfo
import com.projects.moviemanager.domain.models.content.ShowDetailsInfo
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
import com.projects.moviemanager.features.watchlist.model.DataLoadState
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistCard
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistTabItem

@Composable
fun Watchlist(
    goToDetails: (Int, MediaType) -> Unit
) {
    Watchlist(
        viewModel = hiltViewModel(),
        mainViewModel = hiltViewModel(),
        goToDetails = goToDetails
    )
}

@Composable
private fun Watchlist(
    viewModel: WatchlistViewModel,
    mainViewModel: MainViewModel,
    goToDetails: (Int, MediaType) -> Unit
) {
    val loadState by viewModel.loadState.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()
    val watchedList by viewModel.watchedList.collectAsState()
    val selectedList by viewModel.selectedList
    val sortType by mainViewModel.watchlistSort.collectAsState()

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

    LaunchedEffect(sortType) {
        viewModel.onEvent(
            WatchlistEvent.UpdateSortType(sortType)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GenericTabRow(selectedTabIndex.value, tabList, updateSelectedTab)
        when (loadState) {
            DataLoadState.Loading -> {
                WatchlistBodyPlaceholder()
            }
            DataLoadState.Success -> {
                when (tabList[selectedTabIndex.value].tabIndex) {
                    WatchlistTabItem.WatchlistTab.tabIndex -> {
                        WatchlistBody(
                            watchlist = watchlist,
                            sortType = sortType,
                            selectedList = selectedList,
                            goToDetails = goToDetails,
                            removeItem = removeItem
                        )
                    }

                    WatchlistTabItem.WatchedTab.tabIndex -> {
                        WatchlistBody(
                            watchlist = watchedList,
                            sortType = sortType,
                            selectedList = selectedList,
                            goToDetails = goToDetails,
                            removeItem = removeItem
                        )
                    }
                }
            }
            DataLoadState.Failed -> {
                // TODO Handle error state.
            }
        }
    }
}

@Composable
private fun WatchlistBody(
    watchlist: List<DetailedMediaInfo>,
    sortType: MediaType?,
    selectedList: String,
    goToDetails: (Int, MediaType) -> Unit,
    removeItem: (Int, MediaType) -> Unit
) {
    val sortedItems = if (sortType != null) {
        watchlist.filter { it.mediaType == sortType }
    } else {
        watchlist
    }
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_MARGIN.dp)
    ) {
        items(sortedItems) { mediaInfo ->
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
                selectedList = selectedList,
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

@Composable
private fun WatchlistBodyPlaceholder() {
    val imageWidth = WATCHLIST_IMAGE_WIDTH.dp
    val imageHeight = imageWidth * POSTER_ASPECT_RATIO_MULTIPLY
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_MARGIN.dp)
    ) {
        items(4) {
            // Card row
            Row {
                // Image
                ComponentPlaceholder(
                    modifier = Modifier
                        .width(imageWidth)
                        .height(imageHeight)
                        .clip(
                            RoundedCornerShape(
                                topStart = CARD_ROUND_CORNER.dp,
                                bottomStart = CARD_ROUND_CORNER.dp
                            )
                        )
                )
                // Content Description Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(imageHeight)
                        .padding(all = DEFAULT_PADDING.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Title
                        ComponentPlaceholder(
                            modifier = Modifier
                                .weight(1f)
                                .height(20.dp)
                        )
                        Spacer(modifier = Modifier.width(DEFAULT_PADDING.dp))
                    }
                    Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
                    // Rating
                    ComponentPlaceholder(
                        modifier = Modifier
                            .width(40.dp)
                            .height(20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // Content Type Tag
                    ComponentPlaceholder(
                        modifier = Modifier
                            .width(50.dp)
                            .height(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
        }
    }
}
