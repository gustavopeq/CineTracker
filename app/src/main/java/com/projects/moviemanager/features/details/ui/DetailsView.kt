package com.projects.moviemanager.features.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.DimensionSubcomposeLayout
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.ui.util.UiConstants.BACKGROUND_INDEX
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DETAILS_TITLE_IMAGE_OFFSET_PERCENT
import com.projects.moviemanager.common.ui.util.UiConstants.FOREGROUND_INDEX
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO
import com.projects.moviemanager.common.ui.util.UiConstants.RETURN_TOP_BAR_HEIGHT
import com.projects.moviemanager.common.ui.util.UiConstants.SECTION_PADDING
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.features.details.ui.components.CastCarousel
import com.projects.moviemanager.features.details.ui.components.DetailsDescriptionBody
import com.projects.moviemanager.features.details.ui.components.DetailsDescriptionHeader
import com.projects.moviemanager.features.details.ui.components.MoreOptionsTabRow
import com.projects.moviemanager.features.details.util.mapValueToRange
import com.projects.moviemanager.util.Constants.BASE_ORIGINAL_IMAGE_URL
import timber.log.Timber

@Composable
fun Details(
    contentId: Int?,
    mediaType: MediaType,
    onBackPress: () -> Unit,
    openSimilarContent: (Int, MediaType) -> Unit
) {
    Details(
        viewModel = hiltViewModel(),
        contentId = contentId,
        mediaType = mediaType,
        onBackPress = onBackPress,
        openSimilarContent = openSimilarContent
    )
}

@Composable
private fun Details(
    viewModel: DetailsViewModel,
    contentId: Int?,
    mediaType: MediaType,
    onBackPress: () -> Unit,
    openSimilarContent: (Int, MediaType) -> Unit
) {
    val localDensity = LocalDensity.current
    val contentDetails by viewModel.contentDetails.collectAsState()
    var currentHeaderPosY by rememberSaveable { mutableFloatStateOf(0f) }
    var initialHeaderPosY by rememberSaveable { mutableFloatStateOf(0f) }

    val updateHeaderPosition: (Float) -> Unit = {
        if (it > initialHeaderPosY) {
            initialHeaderPosY = it
        }
        currentHeaderPosY = it
    }

    val contentPosterUrl = BASE_ORIGINAL_IMAGE_URL + contentDetails?.poster_path

    LaunchedEffect(Unit) {
        if (contentId != null) {
            viewModel.fetchDetails(contentId, mediaType)
        } else {
            Timber.tag("print").d("ContentId is null!")
        }
    }

    ReturnTopBar(onBackPress)

    DimensionSubcomposeLayout(
        mainContent = { BackgroundPoster(contentPosterUrl, currentHeaderPosY, initialHeaderPosY) },
        dependentContent = { size ->
            val bgOffset = localDensity.run { size.height.toDp() }
            contentDetails?.let { details ->
                DetailsComponent(
                    bgOffset = bgOffset,
                    contentDetails = details,
                    viewModel = viewModel,
                    updateHeaderPosition = updateHeaderPosition,
                    openDetails = openSimilarContent
                )
            }
        }
    )
}

@Composable
private fun ReturnTopBar(onBackPress: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(RETURN_TOP_BAR_HEIGHT.dp)
            .classicVerticalGradientBrush()
            .zIndex(FOREGROUND_INDEX)
    ) {
        IconButton(
            onClick = { onBackPress() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = stringResource(id = R.string.back_arrow_description)
            )
        }
    }
}

@Composable
private fun DetailsComponent(
    bgOffset: Dp,
    contentDetails: DetailedMediaInfo,
    viewModel: DetailsViewModel,
    updateHeaderPosition: (Float) -> Unit,
    openDetails: (Int, MediaType) -> Unit
) {
    val contentCredits by viewModel.contentCredits.collectAsState()
    val videoList by viewModel.contentVideos.collectAsState()
    val contentSimilarList by viewModel.contentSimilar.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAdditionalInfo(contentDetails.id, contentDetails.mediaType)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bgOffset * DETAILS_TITLE_IMAGE_OFFSET_PERCENT)
            )
        }
        item {
            DetailsDescriptionHeader(contentDetails, updateHeaderPosition)
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
                        .padding(DEFAULT_MARGIN.dp)
                ) {
                    DetailsDescriptionBody(
                        contentDetails = contentDetails
                    )
                    Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
                    if (contentCredits.isNotEmpty()) {
                        CastCarousel(
                            contentCredits = contentCredits,
                            openDetails = openDetails
                        )
                    }

                    Spacer(modifier = Modifier.height(SECTION_PADDING.dp))

                    MoreOptionsTabRow(
                        videoList = videoList,
                        contentSimilarList = contentSimilarList,
                        openSimilarContent = openDetails
                    )
                }
            }
        }
    }
}

@Composable
private fun BackgroundPoster(
    contentPosterUrl: String,
    headerPositionY: Float,
    initialHeaderPosY: Float
) {
    val alpha = headerPositionY.mapValueToRange(initialHeaderPosY)

    NetworkImage(
        imageUrl = contentPosterUrl,
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(BACKGROUND_INDEX)
            .aspectRatio(POSTER_ASPECT_RATIO),
        alpha = alpha
    )
}
