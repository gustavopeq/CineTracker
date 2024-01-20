package com.projects.moviemanager.features.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.ContentCast
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.domain.models.content.Videos
import com.projects.moviemanager.domain.models.person.PersonImage
import com.projects.moviemanager.features.details.domain.DetailsInteractor
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {

    private val _contentDetails: MutableStateFlow<DetailedMediaInfo?> =
        MutableStateFlow(null)
    val contentDetails: StateFlow<DetailedMediaInfo?> get() = _contentDetails

    private val _contentCredits: MutableStateFlow<List<ContentCast>> = MutableStateFlow(emptyList())
    val contentCredits: StateFlow<List<ContentCast>> get() = _contentCredits

    private val _contentVideos: MutableStateFlow<List<Videos>> = MutableStateFlow(emptyList())
    val contentVideos: StateFlow<List<Videos>> get() = _contentVideos

    private val _contentSimilar: MutableStateFlow<List<MediaContent>> = MutableStateFlow(
        emptyList()
    )
    val contentSimilar: StateFlow<List<MediaContent>> get() = _contentSimilar

    private val _personCredits: MutableStateFlow<List<MediaContent>> = MutableStateFlow(emptyList())
    val personCredits: StateFlow<List<MediaContent>> get() = _personCredits

    private val _personImages: MutableStateFlow<List<PersonImage>> = MutableStateFlow(emptyList())
    val personImages: StateFlow<List<PersonImage>> get() = _personImages

    private val _contentInListStatus = MutableStateFlow(
        mapOf(
            Pair(DefaultLists.WATCHLIST.listId, false),
            Pair(DefaultLists.WATCHED.listId, false)
        )
    )
    val contentInListStatus: StateFlow<Map<String, Boolean>> get() = _contentInListStatus

    fun fetchDetails(contentId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentDetails.value = detailsInteractor.getContentDetailsById(contentId, mediaType)
            verifyContentInLists(
                contentId = contentId,
                mediaType = mediaType
            )
        }
    }

    fun fetchAdditionalInfo(contentId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentCredits.value = detailsInteractor.getContentCreditsById(contentId, mediaType)
            when (mediaType) {
                MediaType.MOVIE, MediaType.SHOW -> {
                    _contentVideos.value = detailsInteractor.getContentVideosById(
                        contentId,
                        mediaType
                    )
                    _contentSimilar.value = detailsInteractor.getSimilarContentById(
                        contentId,
                        mediaType
                    )
                }
                MediaType.PERSON -> {
                    _personCredits.value = detailsInteractor.getPersonCreditsById(contentId)
                    _personImages.value = detailsInteractor.getPersonImages(contentId)
                }
            }
        }
    }

    /**
     * Verify if content is present in any of the database list.
     */
    private fun verifyContentInLists(
        contentId: Int,
        mediaType: MediaType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _contentInListStatus.value = detailsInteractor.verifyContentInLists(
                contentId = contentId,
                mediaType = mediaType
            )
        }
    }

    /**
     * Function to Add or Remove content from database list. If the item is currently in the list,
     * it'll be removed. If it's not, it'll be added.
     */
    fun toggleContentFromList(contentId: Int, mediaType: MediaType, listId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentStatus = _contentInListStatus.value[listId] ?: false
            val updatedWatchlistStatus = _contentInListStatus.value.toMutableMap()

            when (currentStatus) {
                true -> {
                    detailsInteractor.removeFromWatchlist(contentId, mediaType, listId)
                    updatedWatchlistStatus[listId] = false
                }
                false -> {
                    detailsInteractor.addToWatchlist(contentId, mediaType, listId)
                    updatedWatchlistStatus[listId] = true
                }
            }

            _contentInListStatus.value = updatedWatchlistStatus
        }
    }
}
