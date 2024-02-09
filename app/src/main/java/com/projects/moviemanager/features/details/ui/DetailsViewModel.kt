package com.projects.moviemanager.features.details.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.common.domain.models.content.ContentCast
import com.projects.moviemanager.common.domain.models.content.DetailedContent
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.content.Videos
import com.projects.moviemanager.common.domain.models.person.PersonImage
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.util.UiConstants.DELAY_UPDATE_POPUP_TEXT_MS
import com.projects.moviemanager.features.details.DetailsScreen.ARG_CONTENT_ID
import com.projects.moviemanager.features.details.DetailsScreen.ARG_MEDIA_TYPE
import com.projects.moviemanager.features.details.domain.DetailsInteractor
import com.projects.moviemanager.features.details.ui.events.DetailsEvents
import com.projects.moviemanager.features.details.ui.state.DetailsSnackbarState
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {
    private val contentId: Int = savedStateHandle[ARG_CONTENT_ID] ?: -1
    private val mediaType: MediaType = MediaType.getType(savedStateHandle[ARG_MEDIA_TYPE])

    private val _loadState: MutableStateFlow<DataLoadStatus> = MutableStateFlow(
        DataLoadStatus.Loading
    )
    val loadState: StateFlow<DataLoadStatus> get() = _loadState

    private val _contentDetails: MutableStateFlow<DetailedContent?> =
        MutableStateFlow(null)
    val contentDetails: StateFlow<DetailedContent?> get() = _contentDetails

    private val _contentCredits: MutableStateFlow<List<ContentCast>> = MutableStateFlow(emptyList())
    val contentCredits: StateFlow<List<ContentCast>> get() = _contentCredits

    private val _contentVideos: MutableStateFlow<List<Videos>> = MutableStateFlow(emptyList())
    val contentVideos: StateFlow<List<Videos>> get() = _contentVideos

    private val _contentSimilar: MutableStateFlow<List<GenericContent>> = MutableStateFlow(
        emptyList()
    )
    val contentSimilar: StateFlow<List<GenericContent>> get() = _contentSimilar

    private val _personCredits: MutableStateFlow<List<GenericContent>> = MutableStateFlow(
        emptyList()
    )
    val personCredits: StateFlow<List<GenericContent>> get() = _personCredits

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

    private val _snackbarState: MutableState<DetailsSnackbarState> = mutableStateOf(
        DetailsSnackbarState()
    )
    val snackbarState: MutableState<DetailsSnackbarState> get() = _snackbarState

    init {
        initFetchDetails()
    }

    fun onEvent(
        event: DetailsEvents
    ) {
        when (event) {
            is DetailsEvents.FetchDetails -> initFetchDetails()
            is DetailsEvents.ToggleContentFromList -> {
                toggleContentFromList(
                    listId = event.listId
                )
            }
            is DetailsEvents.OnError -> resetDetails()
            is DetailsEvents.OnSnackbarDismiss -> snackbarDismiss()
        }
    }

    private fun initFetchDetails() {
        _detailsFailedLoading.value = false
        viewModelScope.launch {
            fetchDetails()
            if (_loadState.value == DataLoadStatus.Success) {
                fetchAdditionalInfo()
            }
        }
    }

    private suspend fun fetchDetails() {
        val detailsState = detailsInteractor.getContentDetailsById(contentId, mediaType)

        if (detailsState.isFailed()) {
            _loadState.value = DataLoadStatus.Failed
        } else {
            _contentDetails.value = detailsState.detailsInfo.value
            verifyContentInLists()
            fetchCastDetails()
        }
    }

    private suspend fun fetchCastDetails() {
        val castDetailsState = detailsInteractor.getContentCreditsById(contentId, mediaType)
        if (castDetailsState.isFailed()) {
            _loadState.value = DataLoadStatus.Failed
        } else {
            _contentCredits.value = castDetailsState.detailsCast.value
            _loadState.value = DataLoadStatus.Success
        }
    }

    private suspend fun fetchAdditionalInfo() {
        when (mediaType) {
            MediaType.MOVIE, MediaType.SHOW -> {
                _contentVideos.value = detailsInteractor.getContentVideosById(
                    contentId,
                    mediaType
                )
                _contentSimilar.value = detailsInteractor.getRecommendationsContentById(
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
    private fun verifyContentInLists() {
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
    private fun toggleContentFromList(listId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentStatus = _contentInListStatus.value[listId] ?: false
            val updatedWatchlistStatus = _contentInListStatus.value.toMutableMap()

            detailsInteractor.toggleWatchlist(
                currentStatus = currentStatus,
                contentId = contentId,
                mediaType = mediaType,
                listId = listId
            )
            _snackbarState.value = DetailsSnackbarState(
                listId = listId,
                addedItem = !currentStatus
            ).apply {
                setSnackbarVisible()
            }
            delay(DELAY_UPDATE_POPUP_TEXT_MS)
            updatedWatchlistStatus[listId] = !currentStatus

            _contentInListStatus.value = updatedWatchlistStatus
        }
    }

    private fun snackbarDismiss() {
        _snackbarState.value = DetailsSnackbarState()
    }

    private fun resetDetails() {
        _loadState.value = DataLoadStatus.Loading
        _detailsFailedLoading.value = true
    }
}
