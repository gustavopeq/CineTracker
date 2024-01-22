package com.projects.moviemanager.features.watchlist.domain

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.database.model.ContentEntity
import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.toMovieDetailsInfo
import com.projects.moviemanager.domain.models.content.toShowDetailsInfo
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.models.content.movie.MovieApiResponse
import com.projects.moviemanager.network.models.content.show.ShowApiResponse
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

class WatchlistInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository
) {
    suspend fun getAllItems(listId: String): List<ContentEntity> {
        return databaseRepository.getAllItemsByListId(listId = listId)
    }

    suspend fun getContentDetailsById(
        contentId: Int,
        mediaType: MediaType
    ): DetailedMediaInfo? {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieDetailsById(contentId)
            MediaType.SHOW -> showRepository.getShowDetailsById(contentId)
            else -> return null
        }

        var contentDetails: DetailedMediaInfo? = null
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentDetailsById failed with error: ${response.error}")
                }
                is Left -> {
                    contentDetails = when (mediaType) {
                        MediaType.MOVIE -> (response.value as MovieApiResponse).toMovieDetailsInfo()
                        MediaType.SHOW -> (response.value as ShowApiResponse).toShowDetailsInfo()
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
        databaseRepository.deleteItem(
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
        databaseRepository.moveItemToList(
            contentId = contentId,
            mediaType = mediaType,
            currentListId = currentListId,
            newListId = newListId
        )
    }
}
