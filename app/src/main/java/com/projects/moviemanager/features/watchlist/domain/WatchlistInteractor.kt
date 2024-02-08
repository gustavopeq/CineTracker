package com.projects.moviemanager.features.watchlist.domain

import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.content.toGenericContent
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.database.model.ContentEntity
import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.features.watchlist.ui.state.WatchlistState
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import timber.log.Timber
import javax.inject.Inject

class WatchlistInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository
) {
    private var lastRemovedItem: ContentEntity? = null
    private var lastMovedListId: String? = null

    suspend fun getAllItems(listId: String): List<ContentEntity> {
        return databaseRepository.getAllItemsByListId(listId = listId)
    }

    suspend fun fetchListDetails(
        entityList: List<ContentEntity>
    ): WatchlistState {
        val watchlistState = WatchlistState()
        try {
            val detailedWatchlist = entityList.mapNotNull { entity ->
                getContentDetailsById(
                    contentId = entity.contentId,
                    mediaType = MediaType.getType(entity.mediaType)
                )
            }
            watchlistState.listItems.value = detailedWatchlist
        } catch (e: IllegalStateException) {
            watchlistState.setError(
                errorCode = e.message
            )
        }
        return watchlistState
    }

    private suspend fun getContentDetailsById(
        contentId: Int,
        mediaType: MediaType
    ): GenericContent? {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieDetailsById(contentId)
            MediaType.SHOW -> showRepository.getShowDetailsById(contentId)
            else -> return null
        }

        var contentDetails: GenericContent? = null
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentDetailsById failed with error: ${response.error}")
                    throw IllegalStateException(
                        response.error.code,
                        response.error.exception
                    )
                }
                is Left -> {
                    contentDetails = when (mediaType) {
                        MediaType.MOVIE -> (response.value as MovieResponse).toGenericContent()
                        MediaType.SHOW -> (response.value as ShowResponse).toGenericContent()
                        else -> return@collect
                    }
                }
            }
        }
        return contentDetails
    }

    suspend fun removeContentFromDatabase(
        contentId: Int,
        mediaType: MediaType,
        listId: String
    ) {
        lastRemovedItem = databaseRepository.deleteItem(
            contentId = contentId,
            mediaType = mediaType,
            listId = listId
        )
    }
    suspend fun moveItemToList(
        contentId: Int,
        mediaType: MediaType,
        currentListId: String,
        newListId: String
    ) {
        lastRemovedItem = databaseRepository.moveItemToList(
            contentId = contentId,
            mediaType = mediaType,
            currentListId = currentListId,
            newListId = newListId
        )
        lastMovedListId = newListId
    }

    suspend fun undoItemRemoved() {
        lastRemovedItem?.let { contentEntity ->
            databaseRepository.reinsertItem(contentEntity)
        }
    }

    suspend fun undoMovedItem() {
        lastRemovedItem?.let { contentEntity ->
            databaseRepository.reinsertItem(contentEntity)
            lastMovedListId?.let {
                databaseRepository.deleteItem(
                    contentId = contentEntity.contentId,
                    mediaType = MediaType.getType(contentEntity.mediaType),
                    listId = it
                )
            }
        }
    }
}
