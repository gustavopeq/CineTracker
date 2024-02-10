package com.projects.moviemanager.features.details.domain

import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.content.Videos
import com.projects.moviemanager.common.domain.models.content.toContentCast
import com.projects.moviemanager.common.domain.models.content.toDetailedContent
import com.projects.moviemanager.common.domain.models.content.toGenericContent
import com.projects.moviemanager.common.domain.models.content.toGenericContentList
import com.projects.moviemanager.common.domain.models.content.toVideos
import com.projects.moviemanager.common.domain.models.person.PersonImage
import com.projects.moviemanager.common.domain.models.person.toPersonImage
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.features.details.ui.state.DetailsState
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.network.models.content.common.BaseContentResponse
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.search.ContentPagingResponse
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.person.PersonRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

class DetailsInteractor @Inject constructor(
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository,
    private val personRepository: PersonRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend fun getContentDetailsById(
        contentId: Int,
        mediaType: MediaType
    ): DetailsState {
        val detailsState = DetailsState()
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieDetailsById(contentId)
            MediaType.SHOW -> showRepository.getShowDetailsById(contentId)
            MediaType.PERSON -> personRepository.getPersonDetailsById(contentId)
            else -> return detailsState
        }

        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentDetailsById failed with error: ${response.error}")
                    detailsState.setError(errorCode = response.error.code)
                }
                is Left -> {
                    detailsState.detailsInfo.value = when (mediaType) {
                        MediaType.MOVIE -> (response.value as MovieResponse).toDetailedContent()
                        MediaType.SHOW -> (response.value as ShowResponse).toDetailedContent()
                        MediaType.PERSON -> {
                            (response.value as PersonResponse).toDetailedContent()
                        }
                        else -> {
                            null
                        }
                    }
                }
            }
        }
        return detailsState
    }

    suspend fun getContentCastById(
        contentId: Int,
        mediaType: MediaType
    ): DetailsState {
        val detailsState = DetailsState()

        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieCreditsById(contentId)
            MediaType.SHOW -> showRepository.getShowCreditsById(contentId)
            else -> return detailsState
        }

        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentCreditsById failed with error: ${response.error}")
                    detailsState.setError(errorCode = response.error.code)
                }
                is Left -> {
                    detailsState.detailsCast.value = response.value.cast.map {
                        it.toContentCast()
                    }.filterNot {
                        it.profilePoster.isEmpty()
                    }.sortedBy { it.order ?: Int.MAX_VALUE }
                }
            }
        }
        return detailsState
    }

    suspend fun getContentVideosById(
        contentId: Int,
        mediaType: MediaType
    ): List<Videos> {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieVideosById(contentId)
            MediaType.SHOW -> showRepository.getShowVideosById(contentId)
            else -> return emptyList()
        }

        var videoList: List<Videos> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentVideosById failed with error: ${response.error}")
                }
                is Left -> {
                    videoList = response.value.results.map {
                        it.toVideos()
                    }
                }
            }
        }

        return videoList
    }

    suspend fun getRecommendationsContentById(
        contentId: Int,
        mediaType: MediaType
    ): List<GenericContent> {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getRecommendationsMoviesById(contentId)
            MediaType.SHOW -> showRepository.getRecommendationsShowsById(contentId)
            else -> return emptyList()
        }

        var listOfSimilar: List<GenericContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e(
                        "getRecommendationsContentById failed with error: ${response.error}"
                    )
                }
                is Left -> {
                    listOfSimilar = mapResponseToGenericContent(response)
                    if (listOfSimilar.isEmpty()) {
                        listOfSimilar = getSimilarContentById(
                            contentId = contentId,
                            mediaType = mediaType
                        )
                    }
                }
            }
        }
        return listOfSimilar
    }

    private suspend fun getSimilarContentById(
        contentId: Int,
        mediaType: MediaType
    ): List<GenericContent> {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getSimilarMoviesById(contentId)
            MediaType.SHOW -> showRepository.getSimilarShowsById(contentId)
            else -> return emptyList()
        }

        var listOfSimilar: List<GenericContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getSimilarContentById failed with error: ${response.error}")
                }
                is Left -> {
                    listOfSimilar = mapResponseToGenericContent(response)
                }
            }
        }
        return listOfSimilar
    }

    private fun mapResponseToGenericContent(
        response: Left<ContentPagingResponse<out BaseContentResponse>>
    ): List<GenericContent> {
        return response.value.results
            .filter {
                it.poster_path?.isNotEmpty() == true && it.title?.isNotEmpty() == true
            }
            .mapNotNull {
                it.toGenericContent()
            }
    }

    suspend fun getPersonCreditsById(
        personId: Int
    ): List<GenericContent> {
        val result = personRepository.getPersonCreditsById(personId)

        var mediaContentList: List<GenericContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getPersonCreditsById failed with error: ${response.error}")
                }
                is Left -> {
                    mediaContentList = response.value.cast.toGenericContentList().filterNot {
                        it.name.isEmpty() || it.posterPath.isEmpty()
                    }
                }
            }
        }
        return mediaContentList
    }

    suspend fun getPersonImages(
        personId: Int
    ): List<PersonImage> {
        val result = personRepository.getPersonImagesById(personId)

        var imageList: List<PersonImage> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getPersonImages failed with error: ${response.error}")
                }
                is Left -> {
                    imageList = response.value.profiles?.filter {
                        it?.file_path?.isNotEmpty() == true
                    }?.mapNotNull {
                        it?.toPersonImage()
                    } ?: emptyList()
                }
            }
        }
        return imageList
    }

    suspend fun verifyContentInLists(
        contentId: Int,
        mediaType: MediaType
    ): Map<String, Boolean> {
        val result = databaseRepository.searchItems(
            contentId = contentId,
            mediaType = mediaType
        )
        val contentInListMap = mutableMapOf(
            DefaultLists.WATCHLIST.listId to false,
            DefaultLists.WATCHED.listId to false
        )

        result.forEach { content ->
            contentInListMap[content.listId] = true
        }

        return contentInListMap
    }

    suspend fun toggleWatchlist(
        currentStatus: Boolean,
        contentId: Int,
        mediaType: MediaType,
        listId: String
    ) {
        when (currentStatus) {
            true -> {
                removeFromWatchlist(contentId, mediaType, listId)
            }
            false -> {
                addToWatchlist(contentId, mediaType, listId)
            }
        }
    }

    private suspend fun addToWatchlist(
        contentId: Int,
        mediaType: MediaType,
        listId: String
    ) {
        databaseRepository.insertItem(
            contentId = contentId,
            mediaType = mediaType,
            listId = listId
        )
    }

    suspend fun removeFromWatchlist(
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
}
