package com.projects.moviemanager.features.watchlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import com.projects.moviemanager.features.watchlist.events.WatchlistEvent
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val watchlistDatabaseItems = watchlistInteractor.getAllItems()

            val watchlistDetails = watchlistDatabaseItems.mapNotNull {
                Timber.tag("print").d("watchlist: ${it.contentId} - ${it.listId}")
                watchlistInteractor.getContentDetailsById(
                    contentId = it.contentId,
                    mediaType = MediaType.getType(it.mediaType)
                )
            }
            _watchlist.value = watchlistDetails
        }
    }

    fun onEvent(
        event: WatchlistEvent
    ) {
        when (event) {
            is WatchlistEvent.RemoveItem -> removeListItem(event.contentId, event.mediaType)
        }
    }

    private fun removeListItem(
        contentId: Int,
        mediaType: MediaType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            watchlistInteractor.removeContentFromDatabase(
                contentId = contentId,
                mediaType = mediaType
            )

            val updatedList = _watchlist.value.filterNot {
                it.id == contentId && it.mediaType == mediaType
            }
            _watchlist.value = updatedList
        }
    }
}
