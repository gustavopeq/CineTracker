package com.projects.moviemanager.compose.features.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.compose.features.details.domain.DetailsInteractor
import com.projects.moviemanager.compose.features.details.ui.state.ContentDetailsState
import com.projects.moviemanager.domain.models.content.MediaContentDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {

    private val _contentDetails: MutableStateFlow<MediaContentDetails?> =
        MutableStateFlow(null)
    val contentDetails: StateFlow<MediaContentDetails?> get() = _contentDetails

    fun printDetails(contentId: Int, mediaType: MediaType) {
        viewModelScope.launch {
            _contentDetails.value = detailsInteractor.getContentDetailsById(contentId, mediaType)
            Timber.tag("print").d("details: ${_contentDetails.value}")
        }
    }
}
