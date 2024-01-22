package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
import com.projects.moviemanager.features.watchlist.model.DataLoadState
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.features.watchlist.model.DefaultLists.Companion.getOtherList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistInteractor: WatchlistInteractor
) : ViewModel() {

    private val _loadState: MutableStateFlow<DataLoadState> = MutableStateFlow(DataLoadState.Empty)
    val loadState: StateFlow<DataLoadState> get() = _loadState

    private val _watchlist = MutableStateFlow(listOf<DetailedMediaInfo>())
    val watchlist: StateFlow<List<DetailedMediaInfo>> get() = _watchlist

    private val _watchedList = MutableStateFlow(listOf<DetailedMediaInfo>())
    val watchedList: StateFlow<List<DetailedMediaInfo>> get() = _watchedList

    val selectedList = mutableStateOf(DefaultLists.WATCHLIST.listId)

    private val sortType: MutableState<MediaType?> = mutableStateOf(null)

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
                _loadState.value = DataLoadState.Loading
            }

            val watchlistDetails = watchlistDatabaseItems.mapNotNull {
                watchlistInteractor.getContentDetailsById(
                    contentId = it.contentId,
                    mediaType = MediaType.getType(it.mediaType)
                )
            }
            _watchlist.value = watchlistDetails

            val watchedDatabaseItems = watchlistInteractor.getAllItems(
                listId = DefaultLists.WATCHED.listId
            )

            val watchedDetails = watchedDatabaseItems.mapNotNull {
                watchlistInteractor.getContentDetailsById(
                    contentId = it.contentId,
                    mediaType = MediaType.getType(it.mediaType)
                )
            }
            _watchedList.value = watchedDetails

            _loadState.value = DataLoadState.Success
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
        }
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
}
