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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.projects.moviemanager.compose.common.ui.util.UiConstants.DETAILS_TITLE_IMAGE_OFFSET_PERCENT
import com.projects.moviemanager.compose.common.ui.util.UiConstants.EXTRA_MARGIN
import com.projects.moviemanager.compose.common.ui.util.UiConstants.FOREGROUND_INDEX
import com.projects.moviemanager.compose.common.ui.util.UiConstants.POSTER_ASPECT_RATIO
import com.projects.moviemanager.compose.features.details.ui.components.DetailsDescriptionHeader
import com.projects.moviemanager.compose.features.details.ui.components.MovieDetailsDescription
import com.projects.moviemanager.domain.models.content.MediaContentDetails
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
            if (contentDetails != null) {
                DetailsComponent(bgOffset, scrollState, contentDetails)
            }
        }
    )
}

@Composable
private fun DetailsComponent(
    bgOffset: Dp,
    scrollState: LazyListState,
    contentDetails: MediaContentDetails?
) {
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
                    MovieDetailsDescription(
                        contentDetails = contentDetails
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
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
