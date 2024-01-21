package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.ComponentPlaceholder
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.common.ui.util.UiConstants.CARD_ROUND_CORNER
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_MARGIN
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
                            contentList = watchlist,
                            sortType = sortType,
                            selectedList = selectedList,
                            goToDetails = goToDetails,
                            removeItem = removeItem
                        )
                    }
                    WatchlistTabItem.WatchedTab.tabIndex -> {
                        WatchlistBody(
                            contentList = watchedList,
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
    contentList: List<DetailedMediaInfo>,
    sortType: MediaType?,
    selectedList: String,
    goToDetails: (Int, MediaType) -> Unit,
    removeItem: (Int, MediaType) -> Unit
) {
    if (contentList.isNotEmpty()) {
        WatchlistContentLazyList(
            sortType = sortType,
            contentList = contentList,
            selectedList = selectedList,
            goToDetails = goToDetails,
            removeItem = removeItem
        )
    } else {
        EmptyListMessage()
    }
}

@Composable
private fun WatchlistContentLazyList(
    sortType: MediaType?,
    contentList: List<DetailedMediaInfo>,
    selectedList: String,
    goToDetails: (Int, MediaType) -> Unit,
    removeItem: (Int, MediaType) -> Unit
) {
    val sortedItems = if (sortType != null) {
        contentList.filter { it.mediaType == sortType }
    } else {
        contentList
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
        items(5) {
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

@Composable
private fun EmptyListMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        Text(
            text = stringResource(id = R.string.empty_list_header),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.empty_list_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(0.7f))
    }
}
