package com.projects.moviemanager.compose.features.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.R
import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.compose.common.ui.components.DimensionSubcomposeLayout
import com.projects.moviemanager.compose.common.ui.components.NetworkImage
import com.projects.moviemanager.compose.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BACKGROUND_INDEX
import com.projects.moviemanager.compose.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.compose.common.ui.util.UiConstants.DETAILS_CAST_PICTURE_SIZE
import com.projects.moviemanager.compose.common.ui.util.UiConstants.DETAILS_TITLE_IMAGE_OFFSET_PERCENT
import com.projects.moviemanager.compose.common.ui.util.UiConstants.EXTRA_MARGIN
import com.projects.moviemanager.compose.common.ui.util.UiConstants.EXTRA_PADDING
import com.projects.moviemanager.compose.common.ui.util.UiConstants.FOREGROUND_INDEX
import com.projects.moviemanager.compose.common.ui.util.UiConstants.POSTER_ASPECT_RATIO
import com.projects.moviemanager.compose.common.ui.util.UiConstants.SECTION_PADDING
import com.projects.moviemanager.compose.features.details.ui.components.DetailDescriptionLabel
import com.projects.moviemanager.compose.features.details.ui.components.DetailsDescriptionBody
import com.projects.moviemanager.compose.features.details.ui.components.DetailsDescriptionHeader
import com.projects.moviemanager.domain.models.content.ContentCast
import com.projects.moviemanager.domain.models.content.MediaContentDetails
import com.projects.moviemanager.util.Constants.BASE_500_IMAGE_URL
import com.projects.moviemanager.util.Constants.BASE_ORIGINAL_IMAGE_URL
import timber.log.Timber

@Composable
fun Details(
    contentId: Int?,
    mediaType: MediaType,
    onBackPress: () -> Unit
) {
    Details(
        viewModel = hiltViewModel(),
        contentId = contentId,
        mediaType = mediaType,
        onBackPress = onBackPress
    )
}

@Composable
private fun Details(
    viewModel: DetailsViewModel,
    contentId: Int?,
    mediaType: MediaType,
    onBackPress: () -> Unit
) {
    val localDensity = LocalDensity.current
    val contentDetails by viewModel.contentDetails.collectAsState()
    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (contentId != null) {
            viewModel.printDetails(contentId, mediaType)
        } else {
            Timber.tag("print").d("ContentId is null!")
        }
    }

    val contentPosterUrl = BASE_ORIGINAL_IMAGE_URL + contentDetails?.poster_path

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .classicVerticalGradientBrush()
            .zIndex(FOREGROUND_INDEX)
    ) {
        Icon(
            modifier = Modifier
                .padding(EXTRA_MARGIN.dp)
                .clickable {
                    onBackPress()
                },
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = stringResource(id = R.string.back_arrow_description)
        )
    }

    DimensionSubcomposeLayout(
        mainContent = { BackgroundPoster(contentPosterUrl) },
        dependentContent = { size ->
            val bgOffset = localDensity.run { size.height.toDp() }
            contentDetails?.let { details ->
                DetailsComponent(bgOffset, scrollState, details, viewModel)
            }
        }
    )
}

@Composable
private fun DetailsComponent(
    bgOffset: Dp,
    scrollState: LazyListState,
    contentDetails: MediaContentDetails,
    viewModel: DetailsViewModel
) {
    val contentCredits by viewModel.contentCredits.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchContentCredits(contentDetails.id, contentDetails.mediaType)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        state = scrollState
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bgOffset * DETAILS_TITLE_IMAGE_OFFSET_PERCENT)
            )
        }
        item {
            DetailsDescriptionHeader(contentDetails)
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
                        .padding(EXTRA_MARGIN.dp)
                ) {
                    DetailsDescriptionBody(
                        contentDetails = contentDetails
                    )
                    Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
                    if (contentCredits.isNotEmpty()) {
                        CastCarousel(contentCredits)
                    }
                }
                Spacer(modifier = Modifier.height(EXTRA_MARGIN.dp))
            }
        }
    }
}

@Composable
private fun CastCarousel(contentCredits: List<ContentCast>) {
    DetailDescriptionLabel(
        labelText = stringResource(id = R.string.movie_details_cast_label),
        textStyle = MaterialTheme.typography.displayMedium
    )

    Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))

    LazyRow(
        modifier = Modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = constraints.maxWidth + EXTRA_MARGIN.dp.roundToPx()
                    )
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
    ) {
        items(contentCredits) { cast ->
            val castImageUrl = BASE_500_IMAGE_URL + cast.profilePoster

            Column(
                modifier = Modifier
                    .width(DETAILS_CAST_PICTURE_SIZE.dp + EXTRA_PADDING.dp)
                    .padding(horizontal = EXTRA_PADDING.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NetworkImage(
                    imageUrl = castImageUrl,
                    modifier = Modifier
                        .size(DETAILS_CAST_PICTURE_SIZE.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = cast.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                Text(
                    text = cast.character,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
}

@Composable
private fun BackgroundPoster(
    contentPosterUrl: String
) {
    NetworkImage(
        imageUrl = contentPosterUrl,
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(BACKGROUND_INDEX)
            .aspectRatio(POSTER_ASPECT_RATIO)
    )
}
