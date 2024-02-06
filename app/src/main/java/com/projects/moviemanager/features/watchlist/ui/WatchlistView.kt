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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.common.domain.models.content.MovieDetailsInfo
import com.projects.moviemanager.common.domain.models.content.ShowDetailsInfo
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.ComponentPlaceholder
import com.projects.moviemanager.common.ui.components.popup.ClassicSnackbar
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.common.util.UiConstants.CARD_ROUND_CORNER
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.common.util.UiConstants.WATCHLIST_IMAGE_WIDTH
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistCard
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistTabItem

@Composable
fun Watchlist(
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    Watchlist(
        viewModel = hiltViewModel(),
        mainViewModel = hiltViewModel(),
        goToDetails = goToDetails,
        goToErrorScreen = goToErrorScreen
    )
}

@Composable
private fun Watchlist(
    viewModel: WatchlistViewModel,
    mainViewModel: MainViewModel,
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    val loadState by viewModel.loadState.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()
    val watchedList by viewModel.watchedList.collectAsState()
    val selectedList by viewModel.selectedList
    val sortType by mainViewModel.watchlistSort.collectAsState()
    val snackbarState by viewModel.snackbarState
    val snackbarHostState = remember { SnackbarHostState() }

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

    val moveItemToList: (Int, MediaType) -> Unit = { contentId, mediaType ->
        viewModel.onEvent(
            WatchlistEvent.UpdateItemListId(contentId, mediaType)
        )
    }

    LaunchedEffect(sortType) {
        viewModel.onEvent(
            WatchlistEvent.UpdateSortType(sortType)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(
            WatchlistEvent.LoadWatchlistData
        )
    }

    val context = LocalContext.current
    LaunchedEffect(snackbarState) {
        if (snackbarState.displaySnackbar.value) {
            val listName = DefaultLists.getListById(snackbarState.listId)
            listName?.let { list ->
                val listCapitalized = list.toString()
                val message = context.resources.getString(
                    R.string.snackbar_item_removed_from_list,
                    listCapitalized
                )
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    ClassicSnackbar(
        snackbarHostState = snackbarHostState,
        onActionClick = {
            viewModel.onEvent(WatchlistEvent.UndoItemRemoved)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GenericTabRow(selectedTabIndex.value, tabList, updateSelectedTab)
            when (loadState) {
                DataLoadStatus.Empty -> {
                    // Display empty screen
                }

                DataLoadStatus.Loading -> {
                    WatchlistBodyPlaceholder()
                }

                DataLoadStatus.Success -> {
                    when (tabList[selectedTabIndex.value].tabIndex) {
                        WatchlistTabItem.WatchlistTab.tabIndex -> {
                            WatchlistBody(
                                contentList = watchlist,
                                sortType = sortType,
                                selectedList = selectedList,
                                goToDetails = goToDetails,
                                removeItem = removeItem,
                                moveItemToList = moveItemToList
                            )
                        }

                        WatchlistTabItem.WatchedTab.tabIndex -> {
                            WatchlistBody(
                                contentList = watchedList,
                                sortType = sortType,
                                selectedList = selectedList,
                                goToDetails = goToDetails,
                                removeItem = removeItem,
                                moveItemToList = moveItemToList
                            )
                        }
                    }
                }

                DataLoadStatus.Failed -> {
                    goToErrorScreen()
                }
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
    removeItem: (Int, MediaType) -> Unit,
    moveItemToList: (Int, MediaType) -> Unit
) {
    if (contentList.isNotEmpty()) {
        WatchlistContentLazyList(
            sortType = sortType,
            contentList = contentList,
            selectedList = selectedList,
            goToDetails = goToDetails,
            removeItem = removeItem,
            moveItemToList = moveItemToList
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
    removeItem: (Int, MediaType) -> Unit,
    moveItemToList: (Int, MediaType) -> Unit
) {
    val sortedItems = if (sortType != null) {
        contentList.filter { it.mediaType == sortType }
    } else {
        contentList
    }
    if (sortedItems.isNotEmpty()) {
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
                    },
                    onMoveItemToList = {
                        moveItemToList(mediaInfo.id, mediaInfo.mediaType)
                    }
                )
                Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
            }
        }
    } else {
        EmptyListMessage(mediaType = sortType)
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
                    Spacer(modifier = Modifier.height(10.dp))
                    // Rating
                    ComponentPlaceholder(
                        modifier = Modifier
                            .width(50.dp)
                            .height(17.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // Content Type Tag
                    ComponentPlaceholder(
                        modifier = Modifier
                            .width(50.dp)
                            .height(19.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
        }
    }
}

@Composable
private fun EmptyListMessage(
    mediaType: MediaType? = null
) {
    val messageText = when (mediaType) {
        MediaType.MOVIE -> stringResource(id = R.string.empty_movie_list_message)
        MediaType.SHOW -> stringResource(id = R.string.empty_show_list_message)
        else -> stringResource(id = R.string.empty_list_message)
    }
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
            text = messageText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(0.7f))
    }
}
