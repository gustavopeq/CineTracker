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
import com.projects.moviemanager.features.watchlist.ui.components.WatchlistTabItem
import com.projects.moviemanager.features.watchlist.ui.state.WatchlistSnackbarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistInteractor: WatchlistInteractor
) : ViewModel() {

    private val _loadState: MutableStateFlow<DataLoadStatus> = MutableStateFlow(
        DataLoadStatus.Empty
    )
    val loadState: StateFlow<DataLoadStatus> get() = _loadState

    private val _allLists = MutableStateFlow(listOf<WatchlistTabItem>())
    val allLists: StateFlow<List<WatchlistTabItem>> get() = _allLists

    private val _listContent = MutableStateFlow<Map<Int, List<GenericContent>>>(emptyMap())
    val listContent: StateFlow<Map<Int, List<GenericContent>>> get() = _listContent

    val selectedList = mutableStateOf(DefaultLists.WATCHLIST.listId)

    private val sortType: MutableState<MediaType?> = mutableStateOf(null)

    private val _snackbarState: MutableState<WatchlistSnackbarState> = mutableStateOf(
        WatchlistSnackbarState()
    )
    val snackbarState: MutableState<WatchlistSnackbarState> get() = _snackbarState
    private var lastItemAction: WatchlistItemAction? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadAllLists()
        }
    }

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
            is WatchlistEvent.CreateNewList -> createNewList()
            is WatchlistEvent.LoadAllLists -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loadAllLists()
                }
            }
        }
    }

    private fun loadWatchlistData(
        showLoadingState: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val allListsData = mutableMapOf<Int, List<GenericContent>>()

            allLists.value.forEach { listItem ->
                val databaseItems = watchlistInteractor.getAllItems(listItem.listId)

                if (showLoadingState && databaseItems.isNotEmpty()) {
                    _loadState.value = DataLoadStatus.Loading
                }

                val listState = watchlistInteractor.fetchListDetails(databaseItems)
                if (listState.isFailed()) {
                    _loadState.value = DataLoadStatus.Failed
                    return@launch
                }

                allListsData[listItem.listId] = listState.listItems.value
            }

            _listContent.value = allListsData
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
        val listId = selectedList.value

        _listContent.value = _listContent.value.toMutableMap().apply {
            this[listId] = this[listId]?.filterNot {
                it.id == contentId && it.mediaType == mediaType
            } ?: emptyList()
        }
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

    private suspend fun loadAllLists() {
        _allLists.value = watchlistInteractor.getAllLists()
    }

    private fun createNewList() {
        viewModelScope.launch(Dispatchers.IO) {
            watchlistInteractor.createNewList(UUID.randomUUID().toString().substring(0,5))
            loadAllLists()
            loadWatchlistData(
                showLoadingState = true
            )
        }
    }
}
