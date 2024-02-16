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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.content.DetailedContent
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.util.DataLoadStatus
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.popup.ClassicSnackbar
import com.projects.moviemanager.common.util.Constants.BASE_ORIGINAL_IMAGE_URL
import com.projects.moviemanager.common.util.UiConstants.BACKGROUND_INDEX
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.DETAILS_TITLE_IMAGE_OFFSET_PERCENT
import com.projects.moviemanager.common.util.UiConstants.POSTER_ASPECT_RATIO
import com.projects.moviemanager.common.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.util.UiConstants.SECTION_PADDING
import com.projects.moviemanager.features.details.ui.components.CastCarousel
import com.projects.moviemanager.features.details.ui.components.DetailBodyPlaceholder
import com.projects.moviemanager.features.details.ui.components.DetailsDescriptionBody
import com.projects.moviemanager.features.details.ui.components.DetailsDescriptionHeader
import com.projects.moviemanager.features.details.ui.components.DetailsTopBar
import com.projects.moviemanager.features.details.ui.components.moreoptions.MoreOptionsTab
import com.projects.moviemanager.features.details.ui.components.moreoptions.PersonMoreOptionsTab
import com.projects.moviemanager.features.details.ui.components.showall.ShowAllView
import com.projects.moviemanager.features.details.ui.events.DetailsEvents
import com.projects.moviemanager.features.details.util.mapValueToRange
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.features.watchlist.model.DefaultLists.Companion.getListLocalizedName

@Composable
fun Details(
    navBackStackEntry: NavBackStackEntry,
    onBackPress: () -> Unit,
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Details(
            viewModel = hiltViewModel(navBackStackEntry),
            onBackPress = onBackPress,
            goToDetails = goToDetails,
            goToErrorScreen = goToErrorScreen
        )
    }
}

@Composable
private fun Details(
    viewModel: DetailsViewModel,
    onBackPress: () -> Unit,
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    val contentDetails by viewModel.contentDetails.collectAsState()
    val contentInListStatus by viewModel.contentInListStatus.collectAsState()
    val loadState by viewModel.loadState.collectAsState()
    val detailsFailedLoading by viewModel.detailsFailedLoading
    val snackbarState by viewModel.snackbarState
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val posterWidth = LocalConfiguration.current.screenWidthDp
    val posterHeight = posterWidth * POSTER_ASPECT_RATIO_MULTIPLY
    val personContentList by viewModel.personCredits.collectAsState()
    var showAllScreen by remember { mutableStateOf(false) }
    var showAllMediaType by remember { mutableStateOf(MediaType.MOVIE) }

    var currentTitlePosY by rememberSaveable { mutableFloatStateOf(0f) }
    var initialTitlePosY by rememberSaveable { mutableStateOf<Float?>(null) }

    val updateTitlePosition: (Float) -> Unit = { newPosition ->
        if (initialTitlePosY == null) {
            initialTitlePosY = newPosition
            currentTitlePosY = newPosition
        } else {
            currentTitlePosY = newPosition
        }
    }

    val onToggleWatchlist: (String) -> Unit = { listId ->
        contentDetails?.let {
            viewModel.onEvent(
                DetailsEvents.ToggleContentFromList(
                    listId = listId
                )
            )
        }
    }

    val updateShowAllFlag: (Boolean, MediaType) -> Unit = { flag, mediaType ->
        showAllMediaType = mediaType
        showAllScreen = flag
    }

    LaunchedEffect(Unit) {
        if (detailsFailedLoading) {
            viewModel.onEvent(
                DetailsEvents.FetchDetails
            )
        }
    }

    LaunchedEffect(snackbarState) {
        if (snackbarState.displaySnackbar.value) {
            val itemAdded = snackbarState.addedItem
            val listName = DefaultLists.getListById(snackbarState.listId)
            listName?.let {
                val listLocalizedName = context.resources.getString(getListLocalizedName(listName))
                val message = if (itemAdded) {
                    context.resources.getString(
                        R.string.snackbar_item_added_in_list,
                        listLocalizedName
                    )
                } else {
                    context.resources.getString(
                        R.string.snackbar_item_removed_from_list,
                        listLocalizedName
                    )
                }
                snackbarHostState.showSnackbar(message)
                viewModel.onEvent(DetailsEvents.OnSnackbarDismiss)
            }
        }
    }

    if (loadState is DataLoadStatus.Success && showAllScreen) {
        ShowAllView(
            showAllMediaType = showAllMediaType,
            contentList = personContentList,
            goToDetails = goToDetails,
            onBackBtnPress = { updateShowAllFlag(false, MediaType.UNKNOWN) }
        )
    } else {
        DetailsTopBar(
            contentTitle = contentDetails?.name.orEmpty(),
            currentHeaderPosY = currentTitlePosY,
            initialHeaderPosY = initialTitlePosY,
            showWatchlistButton = contentDetails?.mediaType != MediaType.PERSON,
            contentInWatchlistStatus = contentInListStatus,
            onBackBtnPress = onBackPress,
            toggleWatchlist = onToggleWatchlist
        )

        ClassicSnackbar(
            snackbarHostState = snackbarHostState
        ) {
            when (loadState) {
                is DataLoadStatus.Loading -> {
                    DetailBodyPlaceholder(posterHeight)
                }

                is DataLoadStatus.Success -> {
                    DetailsBody(
                        posterHeight = posterHeight,
                        viewModel = viewModel,
                        contentDetails = contentDetails,
                        personContentList = personContentList,
                        initialTitlePosY = initialTitlePosY,
                        currentTitlePosY = currentTitlePosY,
                        updateTitlePosition = updateTitlePosition,
                        goToDetails = goToDetails,
                        updateShowAllFlag = updateShowAllFlag
                    )
                }

                else -> {
                    viewModel.onEvent(DetailsEvents.OnError)
                    goToErrorScreen()
                }
            }
        }
    }
}

@Composable
private fun DetailsBody(
    posterHeight: Float,
    viewModel: DetailsViewModel,
    contentDetails: DetailedContent?,
    personContentList: List<GenericContent>,
    currentTitlePosY: Float,
    initialTitlePosY: Float?,
    updateTitlePosition: (Float) -> Unit,
    goToDetails: (Int, MediaType) -> Unit,
    updateShowAllFlag: (Boolean, MediaType) -> Unit
) {
    val contentPosterUrl = BASE_ORIGINAL_IMAGE_URL + contentDetails?.posterPath

    BackgroundPoster(
        posterHeight = posterHeight,
        contentPosterUrl = contentPosterUrl,
        titlePositionY = currentTitlePosY,
        initialTitlePosY = initialTitlePosY
    )
    contentDetails?.let { details ->
        DetailsComponent(
            posterHeight = posterHeight,
            mediaInfo = details,
            viewModel = viewModel,
            personContentList = personContentList,
            updateTitlePosition = updateTitlePosition,
            goToDetails = goToDetails,
            updateShowAllFlag = updateShowAllFlag
        )
    }
}

@Composable
private fun DetailsComponent(
    posterHeight: Float,
    mediaInfo: DetailedContent,
    viewModel: DetailsViewModel,
    personContentList: List<GenericContent>,
    updateTitlePosition: (Float) -> Unit,
    goToDetails: (Int, MediaType) -> Unit,
    updateShowAllFlag: (Boolean, MediaType) -> Unit
) {
    val contentCredits by viewModel.contentCredits.collectAsState()
    val videoList by viewModel.contentVideos.collectAsState()
    val contentSimilarList by viewModel.contentSimilar.collectAsState()
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
            DetailsDescriptionHeader(mediaInfo, updateTitlePosition)
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
                    if (contentCredits.isNotEmpty()) {
                        CastCarousel(
                            contentCredits = contentCredits,
                            goToDetails = goToDetails
                        )
                        Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
                    }

                    when (mediaInfo.mediaType) {
                        MediaType.MOVIE, MediaType.SHOW -> {
                            MoreOptionsTab(
                                videoList = videoList,
                                contentSimilarList = contentSimilarList,
                                goToDetails = goToDetails
                            )
                        }
                        MediaType.PERSON -> {
                            PersonMoreOptionsTab(
                                contentList = personContentList,
                                personImageList = personImageList,
                                goToDetails = goToDetails,
                                updateShowAllFlag = updateShowAllFlag
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
    titlePositionY: Float,
    initialTitlePosY: Float?
) {
    val alpha = if (initialTitlePosY != null) {
        titlePositionY.mapValueToRange(initialTitlePosY)
    } else {
        1f
    }

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
