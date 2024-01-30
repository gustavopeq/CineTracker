package com.projects.moviemanager.features.home.ui.components.featured

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.GradientDirections
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.domain.models.content.GenericContent

@Composable
fun FeaturedInfo(
    mainContent: GenericContent,
    goToDetails: (Int, MediaType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .classicVerticalGradientBrush(
                direction = GradientDirections.UP
            )
    ) {
        Column(
            modifier = Modifier.padding(UiConstants.DEFAULT_MARGIN.dp)
        ) {
            Text(
                text = mainContent.name.orEmpty(),
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(UiConstants.DEFAULT_PADDING.dp))
            Text(
                text = "Aquaman, an exhilarating underwater adventure movie, dives into the " +
                    "visually stunning world of Atlantis. The story revolves around Arthur " +
                    "Curry, the reluctant ruler of the underwater kingdom.",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(UiConstants.DEFAULT_PADDING.dp))
            Button(
                onClick = {
                    goToDetails(mainContent.id, mainContent.mediaType)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.see_details_button_text),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(UiConstants.DEFAULT_PADDING.dp))
        }
    }
}

@Composable
fun FeaturedBackgroundImage(
    imageUrl: String
) {
    Box {
        NetworkImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(UiConstants.BACKGROUND_INDEX)
                .aspectRatio(UiConstants.POSTER_ASPECT_RATIO)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    MaterialTheme.colorScheme.primary.copy(UiConstants.HOME_BACKGROUND_ALPHA)
                )
        )
    }
}
