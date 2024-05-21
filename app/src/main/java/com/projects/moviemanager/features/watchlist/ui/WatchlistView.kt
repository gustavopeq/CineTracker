package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.popup.ClassicSnackbar
import com.projects.moviemanager.common.ui.components.popup.GenericPopupMenu
import com.projects.moviemanager.common.ui.components.popup.PopupMenuItem
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.util.Constants.UNSELECTED_OPTION_INDEX
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.features.watchlist.WatchlistScreen
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.features.watchlist.model.DefaultLists.Companion.getListLocalizedName
import com.projects.moviemanager.features.watchlist.model.WatchlistItemAction
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistBodyPlaceholder
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistCard
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistTabItem
import com.projects.moviemanager.features.watchlist.ui.state.WatchlistSnackbarState

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
    val allLists by viewModel.allLists.collectAsState()
    val listContent by viewModel.listContent.collectAsState()
    val selectedList by viewModel.selectedList
    val sortType by mainViewModel.watchlistSort.collectAsState()
    val snackbarState by viewModel.snackbarState
    val snackbarHostState = remember { SnackbarHostState() }

    if (allLists.isNotEmpty()) {
        AllListsLoadedState(
            allLists,
            viewModel,
            sortType,
            mainViewModel,
            snackbarState,
            snackbarHostState,
            loadState,
            listContent,
            selectedList,
            goToDetails,
            goToErrorScreen
        )
    }
}

@Composable
private fun AllListsLoadedState(
    tabList: List<WatchlistTabItem>,
    viewModel: WatchlistViewModel,
    sortType: MediaType?,
    mainViewModel: MainViewModel,
    snackbarState: WatchlistSnackbarState,
    snackbarHostState: SnackbarHostState,
    loadState: DataLoadStatus,
    listContent: Map<Int, List<GenericContent>>,
    selectedList: Int,
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    val refreshLists by mainViewModel.refreshLists.collectAsState()
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    var showRemovePopUpMenu by remember { mutableStateOf(false) }
    var removePopUpMenuOffset by remember { mutableStateOf(Offset.Zero) }
    val listToRemoveIndex = remember { mutableIntStateOf(UNSELECTED_OPTION_INDEX) }

    val updateSelectedTab: (Int) -> Unit = { index ->
        if (tabList[index].listId == WatchlistTabItem.AddNewTab.listId) {
            mainViewModel.updateDisplayCreateNewList(true)
        } else {
            viewModel.onEvent(
                WatchlistEvent.SelectList(tabList[index])
            )
        }
    }

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
        mainViewModel.updateCurrentScreen(WatchlistScreen.route())

        viewModel.onEvent(
            WatchlistEvent.LoadWatchlistData
        )
    }

    LaunchedEffect(refreshLists) {
        if (refreshLists) {
            mainViewModel.setRefreshLists(false)
            viewModel.onEvent(WatchlistEvent.LoadAllLists)
        }
    }

    SnackbarLaunchedEffect(snackbarState, snackbarHostState, viewModel)

    ClassicSnackbar(
        snackbarHostState = snackbarHostState,
        onActionClick = {
            viewModel.onEvent(WatchlistEvent.UndoItemAction)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GenericTabRow(
                selectedTabIndex = selectedTabIndex,
                tabList = tabList,
                updateSelectedTab = { index, _ ->
                    updateSelectedTab(index)
                },
                onLongClick = { index, offset ->
                    if (index != WatchlistTabItem.WatchlistTab.tabIndex &&
                        index != WatchlistTabItem.WatchedTab.tabIndex
                    ) {
                        listToRemoveIndex.intValue = index
                        removePopUpMenuOffset = offset
                        showRemovePopUpMenu = true
                    }
                }
            )
            when (loadState) {
                DataLoadStatus.Empty -> {
                    // Display empty screen
                }

                DataLoadStatus.Loading -> {
                    WatchlistBodyPlaceholder()
                }

                DataLoadStatus.Success -> {
                    val contentList = listContent[tabList[selectedTabIndex].listId]

                    WatchlistBody(
                        contentList = contentList.orEmpty(),
                        sortType = sortType,
                        selectedList = selectedList,
                        goToDetails = goToDetails,
                        removeItem = removeItem,
                        moveItemToList = moveItemToList
                    )
                }

                DataLoadStatus.Failed -> {
                    goToErrorScreen()
                }
            }
        }
    }

    ListRemovePopUpMenu(
        showRemoveMenu = showRemovePopUpMenu,
        menuOffset = removePopUpMenuOffset,
        onRemoveList = {
            if (listToRemoveIndex.intValue != UNSELECTED_OPTION_INDEX) {
                viewModel.onEvent(
                    WatchlistEvent.DeleteList(
                        tabList[listToRemoveIndex.intValue].listId
                    )
                )
            }
        },
        onDismiss = {
            showRemovePopUpMenu = false
        }
    )
}

@Composable
private fun SnackbarLaunchedEffect(
    snackbarState: WatchlistSnackbarState,
    snackbarHostState: SnackbarHostState,
    viewModel: WatchlistViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(snackbarState) {
        if (snackbarState.displaySnackbar.value) {
            val listName = DefaultLists.getListById(snackbarState.listId)
            listName?.let {
                val listLocalizedName = context.resources.getString(getListLocalizedName(listName))
                val message = if (snackbarState.itemAction == WatchlistItemAction.ITEM_REMOVED) {
                    context.resources.getString(
                        R.string.snackbar_item_removed_from_list,
                        listLocalizedName
                    )
                } else {
                    context.resources.getString(
                        R.string.snackbar_item_moved_to_list,
                        listLocalizedName
                    )
                }
                snackbarHostState.showSnackbar(message)
                viewModel.onEvent(WatchlistEvent.OnSnackbarDismiss)
            }
        }
    }
}

@Composable
private fun WatchlistBody(
    contentList: List<GenericContent>,
    sortType: MediaType?,
    selectedList: Int,
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
    contentList: List<GenericContent>,
    selectedList: Int,
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
                WatchlistCard(
                    title = mediaInfo.name,
                    rating = mediaInfo.rating,
                    posterUrl = mediaInfo.posterPath,
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

@Composable
private fun ListRemovePopUpMenu(
    showRemoveMenu: Boolean,
    menuOffset: Offset,
    onRemoveList: () -> Unit,
    onDismiss: () -> Unit
) {
    val menuItems = listOf(
        PopupMenuItem(
            stringResource(id = R.string.delete_list_pop_up_item),
            onClick = onRemoveList
        )
    )
    Box(
        modifier = Modifier
            .absoluteOffset(x = (menuOffset.x / 2).dp)
    ) {
        GenericPopupMenu(
            showMenu = showRemoveMenu,
            backgroundColor = MainBarGreyColor,
            onDismissRequest = onDismiss,
            menuItems = menuItems
        )
    }
}
