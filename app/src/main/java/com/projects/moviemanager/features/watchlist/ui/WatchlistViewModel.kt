package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistInteractor: WatchlistInteractor
) : ViewModel() {

    private val _watchlist = MutableStateFlow(listOf<DetailedMediaInfo>())
    val watchlist: StateFlow<List<DetailedMediaInfo>> get() = _watchlist

    private val _watchedList = MutableStateFlow(listOf<DetailedMediaInfo>())
    val watchedList: StateFlow<List<DetailedMediaInfo>> get() = _watchedList

    private val selectedList = mutableStateOf(DefaultLists.WATCHLIST.listId)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val watchlistDatabaseItems = watchlistInteractor.getAllItems(
                listId = DefaultLists.WATCHLIST.listId
            )

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
        }
    }

    fun onEvent(
        event: WatchlistEvent
    ) {
        when (event) {
            is WatchlistEvent.RemoveItem -> removeListItem(event.contentId, event.mediaType)
            is WatchlistEvent.SelectList -> selectedList.value = event.list
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
            when (selectedList.value) {
                DefaultLists.WATCHLIST.listId -> {
                    val updatedList = _watchlist.value.filterNot {
                        it.id == contentId && it.mediaType == mediaType
                    }
                    _watchlist.value = updatedList
                }
                DefaultLists.WATCHED.listId -> {
                    val updatedList = _watchedList.value.filterNot {
                        it.id == contentId && it.mediaType == mediaType
                    }
                    _watchedList.value = updatedList
                }
            }
        }
    }
}
