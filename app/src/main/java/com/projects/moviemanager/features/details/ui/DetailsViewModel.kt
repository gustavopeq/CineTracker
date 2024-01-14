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

    fun fetchDetails(contentId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentDetails.value = detailsInteractor.getContentDetailsById(contentId, mediaType)
        }
    }

    fun fetchAdditionalInfo(mediaId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentCredits.value = detailsInteractor.getContentCreditsById(mediaId, mediaType)
            when (mediaType) {
                MediaType.MOVIE, MediaType.SHOW -> {
                    _contentVideos.value = detailsInteractor.getContentVideosById(
                        mediaId,
                        mediaType
                    )
                    _contentSimilar.value = detailsInteractor.getSimilarContentById(
                        mediaId,
                        mediaType
                    )
                }
                MediaType.PERSON -> {
                    _personCredits.value = detailsInteractor.getPersonCreditsById(mediaId)
                    _personImages.value = detailsInteractor.getPersonImages(mediaId)
                }
            }
        }
    }
}
