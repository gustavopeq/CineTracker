package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.features.watchlist.model.DefaultLists.Companion.getOtherList
import com.projects.moviemanager.features.watchlist.model.WatchlistItemAction
import com.projects.moviemanager.features.watchlist.ui.state.WatchlistSnackbarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistInteractor: WatchlistInteractor
) : ViewModel() {

    private val _loadState: MutableStateFlow<DataLoadStatus> = MutableStateFlow(
        DataLoadStatus.Empty
    )
    val loadState: StateFlow<DataLoadStatus> get() = _loadState

    private val _watchlist = MutableStateFlow(listOf<GenericContent>())
    val watchlist: StateFlow<List<GenericContent>> get() = _watchlist

    private val _watchedList = MutableStateFlow(listOf<GenericContent>())
    val watchedList: StateFlow<List<GenericContent>> get() = _watchedList

    val selectedList = mutableStateOf(DefaultLists.WATCHLIST.listId)

    private val sortType: MutableState<MediaType?> = mutableStateOf(null)

    private val _snackbarState: MutableState<WatchlistSnackbarState> = mutableStateOf(
        WatchlistSnackbarState()
    )
    val snackbarState: MutableState<WatchlistSnackbarState> get() = _snackbarState
    private var lastItemAction: WatchlistItemAction? = null

    fun onEvent(
        event: WatchlistEvent
    ) {
        when (event) {
            is WatchlistEvent.LoadWatchlistData -> loadWatchlistData(showLoadingState = true)
            is WatchlistEvent.RemoveItem -> removeListItem(event.contentId, event.mediaType)
            is WatchlistEvent.SelectList -> selectedList.value = event.list
            is WatchlistEvent.UpdateSortType -> sortType.value = event.mediaType
            is WatchlistEvent.UpdateItemListId -> {
                updateItemListId(event.contentId, event.mediaType)
            }
            is WatchlistEvent.OnSnackbarDismiss -> snackbarDismiss()
            is WatchlistEvent.UndoItemAction -> undoItemRemoved()
        }
    }

    private fun loadWatchlistData(
        showLoadingState: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val watchlistDatabaseItems = watchlistInteractor.getAllItems(
                listId = DefaultLists.WATCHLIST.listId
            )
            if (showLoadingState && watchlistDatabaseItems.isNotEmpty()) {
                _loadState.value = DataLoadStatus.Loading
            }

            val watchlistState = watchlistInteractor.fetchListDetails(watchlistDatabaseItems)
            if (watchlistState.isFailed()) {
                _loadState.value = DataLoadStatus.Failed
                return@launch
            }
            _watchlist.value = watchlistState.listItems.value

            val watchedDatabaseItems = watchlistInteractor.getAllItems(
                listId = DefaultLists.WATCHED.listId
            )

            val watchedState = watchlistInteractor.fetchListDetails(watchedDatabaseItems)
            if (watchedState.isFailed()) {
                _loadState.value = DataLoadStatus.Failed
                return@launch
            }
            _watchedList.value = watchedState.listItems.value

            _loadState.value = DataLoadStatus.Success
        }
    }

    private fun removeListItem(
        contentId: Int,
        mediaType: MediaType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            watchlistInteractor.removeContentFromDatabase(
                contentId = contentId,
                mediaType = mediaType,
                listId = selectedList.value
            )
            removeContentFromUiList(contentId, mediaType)

            triggerSnackbar(
                listId = selectedList.value,
                itemAction = WatchlistItemAction.ITEM_REMOVED
            )
        }
    }

    private fun updateItemListId(
        contentId: Int,
        mediaType: MediaType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val otherListId = getOtherList(selectedList.value).listId
            watchlistInteractor.moveItemToList(
                contentId = contentId,
                mediaType = mediaType,
                currentListId = selectedList.value,
                newListId = otherListId
            )
            loadWatchlistData(showLoadingState = false)

            triggerSnackbar(
                listId = otherListId,
                itemAction = WatchlistItemAction.ITEM_MOVED
            )
        }
    }

    private fun triggerSnackbar(
        listId: Int,
        itemAction: WatchlistItemAction
    ) {
        _snackbarState.value = WatchlistSnackbarState(
            listId = listId,
            itemAction = itemAction
        ).apply {
            setSnackbarVisible()
        }
        lastItemAction = itemAction
    }

    private fun removeContentFromUiList(
        contentId: Int,
        mediaType: MediaType
    ) {
        val currentList = when (selectedList.value) {
            DefaultLists.WATCHLIST.listId -> _watchlist
            DefaultLists.WATCHED.listId -> _watchedList
            else -> return
        }

        val updatedList = currentList.value.filterNot {
            it.id == contentId && it.mediaType == mediaType
        }
        currentList.value = updatedList
    }

    private fun undoItemRemoved() {
        viewModelScope.launch(Dispatchers.IO) {
            lastItemAction?.let { action ->
                if (action == WatchlistItemAction.ITEM_REMOVED) {
                    watchlistInteractor.undoItemRemoved()
                } else {
                    watchlistInteractor.undoMovedItem()
                }
                loadWatchlistData(showLoadingState = false)
            }
        }
    }

    private fun snackbarDismiss() {
        _snackbarState.value = WatchlistSnackbarState()
    }
}
