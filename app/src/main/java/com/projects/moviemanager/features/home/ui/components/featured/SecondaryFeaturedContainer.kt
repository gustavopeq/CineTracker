package com.projects.moviemanager.features.home.ui.components.featured

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.RatingComponent
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.common.ui.util.UiConstants.BACKDROP_ASPECT_RATIO
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_CARD_DEFAULT_ELEVATION
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.domain.models.content.GenericContent
import com.projects.moviemanager.util.Constants.BASE_ORIGINAL_IMAGE_URL

@Composable
fun SecondaryFeaturedInfo(
    featuredItem: GenericContent?,
    goToDetails: (Int, MediaType) -> Unit
) {
    featuredItem?.let {
        val fullImageUrl = BASE_ORIGINAL_IMAGE_URL + featuredItem.backdropPath

        Column(
            modifier = Modifier.padding(horizontal = DEFAULT_MARGIN.dp)
        ) {
            Card(
                modifier = Modifier.clickable(
                    onClick = { goToDetails(featuredItem.id, featuredItem.mediaType) }
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MainBarGreyColor
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = BROWSE_CARD_DEFAULT_ELEVATION.dp
                )
            ) {
                NetworkImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(BACKDROP_ASPECT_RATIO),
                    imageUrl = fullImageUrl
                )

                Column(
                    modifier = Modifier.padding(DEFAULT_PADDING.dp)
                ) {
                    HomeCardTitle(
                        title = featuredItem.name.orEmpty()
                    )
                    if (featuredItem.rating != null && featuredItem.rating > 0.0) {
                        Spacer(modifier = Modifier.height(UiConstants.SMALL_PADDING.dp))
                        RatingComponent(rating = featuredItem.rating)
                    }
                    Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
                    Text(
                        text = featuredItem.overview.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}
