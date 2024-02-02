package com.projects.moviemanager.common.ui.components.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.domain.models.content.GenericContent
import com.projects.moviemanager.util.Constants.BASE_300_IMAGE_URL

@Composable
fun ImageContentCard(
    modifier: Modifier = Modifier,
    item: GenericContent,
    adjustedCardSize: Dp,
    goToDetails: (Int, MediaType) -> Unit
) {
    val fullImageUrl = BASE_300_IMAGE_URL + item.posterPath
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = {
                    goToDetails(item.id, item.mediaType)
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium),
            imageUrl = fullImageUrl,
            widthDp = adjustedCardSize,
            heightDp = adjustedCardSize * POSTER_ASPECT_RATIO_MULTIPLY
        )
        Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
    }
}