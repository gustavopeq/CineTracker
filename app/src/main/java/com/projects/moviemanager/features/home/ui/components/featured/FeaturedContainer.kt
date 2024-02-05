package com.projects.moviemanager.features.home.ui.components.featured

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.GradientDirections
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.button.ClassicButton
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.ui.util.UiConstants.BACKGROUND_INDEX
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.HOME_BACKGROUND_ALPHA
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO

@Composable
fun FeaturedInfo(
    featuredContent: GenericContent?,
    goToDetails: (Int, MediaType) -> Unit
) {
    featuredContent?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .classicVerticalGradientBrush(
                    direction = GradientDirections.UP
                )
        ) {
            Column(
                modifier = Modifier.padding(DEFAULT_MARGIN.dp)
            ) {
                Text(
                    text = featuredContent.name.orEmpty(),
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
                Text(
                    text = featuredContent.overview.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
                ClassicButton(
                    buttonText = stringResource(id = R.string.see_details_button_text),
                    onClick = {
                        goToDetails(featuredContent.id, featuredContent.mediaType)
                    }
                )
                Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
            }
        }
    }
}

@Composable
fun FeaturedBackgroundImage(
    imageUrl: String,
    posterHeight: Float
) {
    Box {
        NetworkImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(posterHeight.dp)
                .zIndex(BACKGROUND_INDEX)
                .aspectRatio(POSTER_ASPECT_RATIO)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    MaterialTheme.colorScheme.primary.copy(HOME_BACKGROUND_ALPHA)
                )
        )
    }
}
