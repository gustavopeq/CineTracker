package com.projects.moviemanager.common.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.theme.MainBarGreyColor
import com.projects.moviemanager.common.theme.RoundCornerShapes
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String?,
    rating: Double?,
    goToDetails: () -> Unit
) {
    val fullImageUrl = Constants.BASE_500_IMAGE_URL + imageUrl

    Card(
        modifier = modifier
            .padding(
                horizontal = UiConstants.BROWSE_CARD_PADDING_HORIZONTAL.dp,
                vertical = UiConstants.BROWSE_CARD_PADDING_VERTICAL.dp
            )
            .size(
                width = UiConstants.BROWSE_CARD_WIDTH.dp,
                height = UiConstants.BROWSE_CARD_HEIGHT.dp
            ),
        onClick = goToDetails,
        colors = CardDefaults.cardColors(
            containerColor = MainBarGreyColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = UiConstants.BROWSE_CARD_DEFAULT_ELEVATION.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = UiConstants.SMALL_PADDING.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            NetworkImage(
                imageUrl = fullImageUrl,
                modifier = Modifier.clip(RoundCornerShapes.small),
                widthDp = UiConstants.BROWSE_CARD_WIDTH.dp,
                heightDp = UiConstants.BROWSE_CARD_IMAGE_HEIGHT.dp
            )
            Spacer(modifier = Modifier.width(UiConstants.SMALL_PADDING.dp))
            Text(
                text = title.orEmpty(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            RatingComponent(rating = rating)
        }
    }
}