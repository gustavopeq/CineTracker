package com.projects.moviemanager.features.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.ContentCast
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.domain.models.content.MediaContentDetails
import com.projects.moviemanager.domain.models.content.Videos
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

    private val _contentDetails: MutableStateFlow<MediaContentDetails?> =
        MutableStateFlow(null)
    val contentDetails: StateFlow<MediaContentDetails?> get() = _contentDetails

    private val _contentCredits: MutableStateFlow<List<ContentCast>> = MutableStateFlow(emptyList())
    val contentCredits: StateFlow<List<ContentCast>> get() = _contentCredits

    private val _contentVideos: MutableStateFlow<List<Videos>> = MutableStateFlow(emptyList())
    val contentVideos: StateFlow<List<Videos>> get() = _contentVideos

    private val _contentSimilar: MutableStateFlow<List<MediaContent>> = MutableStateFlow(
        emptyList()
    )
    val contentSimilar: StateFlow<List<MediaContent>> get() = _contentSimilar

    fun printDetails(contentId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentDetails.value = detailsInteractor.getContentDetailsById(contentId, mediaType)
        }
    }

    fun fetchAdditionalInfo(contentId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentCredits.value = detailsInteractor.getContentCreditsById(contentId, mediaType)
            _contentVideos.value = detailsInteractor.getContentVideosById(contentId, mediaType)
            _contentSimilar.value = detailsInteractor.getSimilarContentById(contentId, mediaType)
        }
    }
}
