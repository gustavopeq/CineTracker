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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
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

    private val _contentInWatchlist = MutableStateFlow(false)
    val contentInWatchlist: StateFlow<Boolean> get() = _contentInWatchlist

    fun fetchDetails(contentId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentDetails.value = detailsInteractor.getContentDetailsById(contentId, mediaType)
            verifyInWatchlist(
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

    private fun verifyInWatchlist(
        contentId: Int,
        mediaType: MediaType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _contentInWatchlist.value = detailsInteractor.isInWatchlist(
                contentId = contentId,
                mediaType = mediaType
            )
        }
    }

    fun toggleWatchlistStatus(contentId: Int, mediaType: MediaType, listId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsInteractor.addToWatchlist(contentId, mediaType, listId)
//            when (contentInWatchlist.value) {
//                true -> {
//                    detailsInteractor.removeFromWatchlist(contentId, mediaType)
//                    _contentInWatchlist.value = false
//                }
//                false -> {
//                    detailsInteractor.addToWatchlist(contentId, mediaType)
//                    _contentInWatchlist.value = true
//                }
//            }
        }
    }
}
