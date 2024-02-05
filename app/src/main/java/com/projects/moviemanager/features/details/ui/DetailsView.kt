package com.projects.moviemanager.features.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.projects.moviemanager.common.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.util.UiConstants.BACKGROUND_INDEX
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DETAILS_TITLE_IMAGE_OFFSET_PERCENT
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.ui.util.UiConstants.SECTION_PADDING
import com.projects.moviemanager.features.details.ui.components.CastCarousel
import com.projects.moviemanager.features.details.ui.components.DetailBodyPlaceholder
import com.projects.moviemanager.features.details.ui.components.DetailsDescriptionBody
import com.projects.moviemanager.features.details.ui.components.DetailsDescriptionHeader
import com.projects.moviemanager.features.details.ui.components.DetailsTopBar
import com.projects.moviemanager.features.details.ui.components.moreoptions.MoreOptionsTab
import com.projects.moviemanager.features.details.ui.components.moreoptions.PersonMoreOptionsTab
import com.projects.moviemanager.features.details.ui.events.DetailsEvents
import com.projects.moviemanager.features.details.util.mapValueToRange
import com.projects.moviemanager.util.Constants.BASE_ORIGINAL_IMAGE_URL

@Composable
fun Details(
    navBackStackEntry: NavBackStackEntry,
    onBackPress: () -> Unit,
    openSimilarContent: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Details(
            viewModel = hiltViewModel(navBackStackEntry),
            onBackPress = onBackPress,
            openSimilarContent = openSimilarContent,
            goToErrorScreen = goToErrorScreen
        )
    }
}

@Composable
private fun Details(
    viewModel: DetailsViewModel,
    onBackPress: () -> Unit,
    openSimilarContent: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    val contentDetails by viewModel.contentDetails.collectAsState()
    val contentInListStatus by viewModel.contentInListStatus.collectAsState()
    val loadState by viewModel.loadState.collectAsState()
    val detailsFailedLoading by viewModel.detailsFailedLoading
    val posterWidth = LocalConfiguration.current.screenWidthDp
    val posterHeight = posterWidth * POSTER_ASPECT_RATIO_MULTIPLY

    val onToggleWatchlist: (String) -> Unit = { listId ->
        contentDetails?.let {
            viewModel.onEvent(
                DetailsEvents.ToggleContentFromList(
                    listId = listId
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        if (detailsFailedLoading) {
            viewModel.onEvent(
                DetailsEvents.FetchDetails
            )
        }
    }

    DetailsTopBar(
        onBackBtnPress = onBackPress,
        showWatchlistButton = contentDetails?.mediaType != MediaType.PERSON,
        contentInWatchlistStatus = contentInListStatus,
        toggleWatchlist = onToggleWatchlist
    )

    when (loadState) {
        is DataLoadStatus.Loading -> {
            DetailBodyPlaceholder(posterHeight)
        }
        is DataLoadStatus.Success -> {
            DetailsBody(
                posterHeight = posterHeight,
                viewModel = viewModel,
                contentDetails = contentDetails,
                openSimilarContent = openSimilarContent
            )
        }
        else -> {
            viewModel.onEvent(DetailsEvents.OnError)
            goToErrorScreen()
        }
    }
}

@Composable
private fun DetailsBody(
    posterHeight: Float,
    viewModel: DetailsViewModel,
    contentDetails: DetailedMediaInfo?,
    openSimilarContent: (Int, MediaType) -> Unit
) {
    var currentHeaderPosY by rememberSaveable { mutableFloatStateOf(0f) }
    var initialHeaderPosY by rememberSaveable { mutableFloatStateOf(0f) }
    val contentPosterUrl = BASE_ORIGINAL_IMAGE_URL + contentDetails?.poster_path

    val updateHeaderPosition: (Float) -> Unit = {
        if (it > initialHeaderPosY) {
            initialHeaderPosY = it
        }
        currentHeaderPosY = it
    }

    BackgroundPoster(
        posterHeight,
        contentPosterUrl,
        currentHeaderPosY,
        initialHeaderPosY
    )
    contentDetails?.let { details ->
        DetailsComponent(
            posterHeight = posterHeight,
            mediaInfo = details,
            viewModel = viewModel,
            updateHeaderPosition = updateHeaderPosition,
            openDetails = openSimilarContent
        )
    }
}

@Composable
private fun DetailsComponent(
    posterHeight: Float,
    mediaInfo: DetailedMediaInfo,
    viewModel: DetailsViewModel,
    updateHeaderPosition: (Float) -> Unit,
    openDetails: (Int, MediaType) -> Unit
) {
    val contentCredits by viewModel.contentCredits.collectAsState()
    val videoList by viewModel.contentVideos.collectAsState()
    val contentSimilarList by viewModel.contentSimilar.collectAsState()
    val personContentList by viewModel.personCredits.collectAsState()
    val personImageList by viewModel.personImages.collectAsState()

    val titleScreenHeight = posterHeight * DETAILS_TITLE_IMAGE_OFFSET_PERCENT

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(titleScreenHeight.dp)
            )
        }
        item {
            DetailsDescriptionHeader(mediaInfo, updateHeaderPosition)
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = DEFAULT_MARGIN.dp, vertical = 0.dp)
                ) {
                    DetailsDescriptionBody(
                        contentDetails = mediaInfo
                    )
                    Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
                    if (contentCredits.isNotEmpty()) {
                        CastCarousel(
                            contentCredits = contentCredits,
                            openDetails = openDetails
                        )
                        Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
                    }

                    when (mediaInfo.mediaType) {
                        MediaType.MOVIE, MediaType.SHOW -> {
                            MoreOptionsTab(
                                videoList = videoList,
                                contentSimilarList = contentSimilarList,
                                openSimilarContent = openDetails
                            )
                        }
                        MediaType.PERSON -> {
                            PersonMoreOptionsTab(
                                contentList = personContentList,
                                personImageList = personImageList,
                                openContentDetails = openDetails
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
private fun BackgroundPoster(
    posterHeight: Float,
    contentPosterUrl: String,
    headerPositionY: Float,
    initialHeaderPosY: Float
) {
    val alpha = headerPositionY.mapValueToRange(initialHeaderPosY)

    NetworkImage(
        imageUrl = contentPosterUrl,
        modifier = Modifier
            .fillMaxWidth()
            .height(posterHeight.dp)
            .zIndex(BACKGROUND_INDEX)
            .aspectRatio(POSTER_ASPECT_RATIO),
        alpha = alpha
    )
}
