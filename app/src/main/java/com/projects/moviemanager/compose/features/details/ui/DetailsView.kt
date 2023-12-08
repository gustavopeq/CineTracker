package com.projects.moviemanager.compose.features.details.ui

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
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
import com.projects.moviemanager.compose.common.ui.util.UiConstants.DETAILS_TITLE_IMAGE_OFFSET_PERCENT
import com.projects.moviemanager.compose.common.ui.util.UiConstants.EXTRA_MARGIN
import com.projects.moviemanager.compose.common.ui.util.UiConstants.FOREGROUND_INDEX
import com.projects.moviemanager.compose.common.ui.util.UiConstants.LARGE_PADDING
import com.projects.moviemanager.compose.common.ui.util.UiConstants.POSTER_ASPECT_RATIO
import com.projects.moviemanager.compose.common.ui.util.UiConstants.SECTION_PADDING
import com.projects.moviemanager.compose.common.ui.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.compose.features.details.ui.components.CastCarousel
import com.projects.moviemanager.compose.features.details.ui.components.DetailsDescriptionBody
import com.projects.moviemanager.compose.features.details.ui.components.DetailsDescriptionHeader
import com.projects.moviemanager.compose.features.details.ui.components.MoreOptionsTabItem
import com.projects.moviemanager.compose.features.details.ui.components.MoreOptionsTabRow
import com.projects.moviemanager.compose.theme.MainBarGreyColor
import com.projects.moviemanager.compose.theme.SecondaryGreyColor
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

                    Spacer(modifier = Modifier.height(SECTION_PADDING.dp))

                    MoreOptionsTabRow()
                }
                Spacer(modifier = Modifier.height(EXTRA_MARGIN.dp))
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
