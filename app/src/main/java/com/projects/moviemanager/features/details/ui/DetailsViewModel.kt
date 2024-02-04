package com.projects.moviemanager.features.details.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.projects.moviemanager.common.domain.models.content.ContentCast
import com.projects.moviemanager.common.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.common.domain.models.content.MediaContent
import com.projects.moviemanager.common.domain.models.content.Videos
import com.projects.moviemanager.common.domain.models.person.PersonImage
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.features.details.DetailsScreen.ARG_ID
import com.projects.moviemanager.features.details.DetailsScreen.ARG_MEDIA_TYPE
import com.projects.moviemanager.features.details.domain.DetailsInteractor
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {
    private val contentId: Int = savedStateHandle[ARG_ID] ?: -1
    private val mediaType: MediaType = MediaType.getType(savedStateHandle[ARG_MEDIA_TYPE])

    private val _loadState: MutableStateFlow<DataLoadStatus> = MutableStateFlow(
        DataLoadStatus.Loading
    )
    val loadState: StateFlow<DataLoadStatus> get() = _loadState

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

    private val _detailsFailedLoading: MutableState<Boolean> = mutableStateOf(false)
    val detailsFailedLoading: MutableState<Boolean> get() = _detailsFailedLoading

    init {
        initFetchDetails()
    }

    fun initFetchDetails() {
        _detailsFailedLoading.value = false
        viewModelScope.launch {
            fetchDetails(
                contentId = contentId,
                mediaType = mediaType
            )
            if (_loadState.value == DataLoadStatus.Success) {
                fetchAdditionalInfo(
                    contentId = contentId,
                    mediaType = mediaType
                )
            }
        }
    }

    private suspend fun fetchDetails(contentId: Int, mediaType: MediaType) {
        val detailsState = detailsInteractor.getContentDetailsById(contentId, mediaType)

        if (detailsState.isFailed()) {
            _loadState.value = DataLoadStatus.Failed
        } else {
            _contentDetails.value = detailsState.detailsInfo.value
            verifyContentInLists(
                contentId = contentId,
                mediaType = mediaType
            )
            fetchCastDetails(
                contentId = contentId,
                mediaType = mediaType
            )
            _loadState.value = DataLoadStatus.Success
        }
    }

    private suspend fun fetchCastDetails(contentId: Int, mediaType: MediaType) {
        val castDetailsState = detailsInteractor.getContentCreditsById(contentId, mediaType)
        if (castDetailsState.isFailed()) {
            _loadState.value = DataLoadStatus.Failed
        } else {
            _contentCredits.value = castDetailsState.detailsCast.value
            _loadState.value = DataLoadStatus.Success
        }
    }

    private suspend fun fetchAdditionalInfo(contentId: Int, mediaType: MediaType) {
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
            else -> {}
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

    fun resetDetails() {
        _loadState.value = DataLoadStatus.Loading
        _detailsFailedLoading.value = true
    }
}
