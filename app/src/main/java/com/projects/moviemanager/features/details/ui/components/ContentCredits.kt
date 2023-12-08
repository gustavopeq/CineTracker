package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.domain.models.content.ContentCast
import com.projects.moviemanager.util.Constants

@Composable
fun CastCarousel(contentCredits: List<ContentCast>) {
    DetailDescriptionLabel(
        labelText = stringResource(id = R.string.movie_details_cast_label),
        textStyle = MaterialTheme.typography.displayMedium
    )

    Spacer(modifier = Modifier.height(UiConstants.SMALL_PADDING.dp))

    LazyRow(
        modifier = Modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = constraints.maxWidth + UiConstants.EXTRA_MARGIN.dp.roundToPx()
                    )
                )
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
    ) {
        items(contentCredits) { cast ->
            val castImageUrl = Constants.BASE_500_IMAGE_URL + cast.profilePoster

            Column(
                modifier = Modifier
                    .width(UiConstants.DETAILS_CAST_PICTURE_SIZE.dp + UiConstants.DEFAULT_PADDING.dp)
                    .padding(horizontal = UiConstants.DEFAULT_PADDING.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NetworkImage(
                    imageUrl = castImageUrl,
                    modifier = Modifier
                        .size(UiConstants.DETAILS_CAST_PICTURE_SIZE.dp)
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
}
