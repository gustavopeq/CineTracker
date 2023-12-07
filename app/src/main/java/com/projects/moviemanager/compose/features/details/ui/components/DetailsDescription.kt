package com.projects.moviemanager.compose.features.details.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.compose.common.ui.components.GradientDirections
import com.projects.moviemanager.compose.common.ui.components.RatingComponent
import com.projects.moviemanager.compose.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.compose.common.ui.util.UiConstants
import com.projects.moviemanager.compose.features.details.util.stringFormat
import com.projects.moviemanager.domain.models.content.MediaContentDetails

@Composable
fun DetailsDescriptionHeader(contentDetails: MediaContentDetails?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .classicVerticalGradientBrush(
                direction = GradientDirections.UP
            )
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UiConstants.EXTRA_MARGIN.dp)
        ) {
            Text(
                text = "${contentDetails?.title}",
                style = MaterialTheme.typography.displayMedium
            )
            RatingComponent(rating = contentDetails?.vote_average)
        }
    }
}

@Composable
fun MovieDetailsDescription(
    contentDetails: MediaContentDetails?
) {
    Text(
        text = "${contentDetails?.overview}",
        style = MaterialTheme.typography.bodyMedium
    )

    Spacer(modifier = Modifier.height(UiConstants.EXTRA_MARGIN.dp))

    if (contentDetails?.production_countries?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_production_country_title)
        )

        contentDetails.production_countries.forEach {
            DetailDescriptionBody(it?.name.orEmpty())
        }
    }

    Spacer(modifier = Modifier.height(UiConstants.EXTRA_MARGIN.dp))

    if (contentDetails?.release_date?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_release_date_label)
        )
        DetailDescriptionBody(contentDetails.release_date)
    }

    Spacer(modifier = Modifier.height(UiConstants.EXTRA_MARGIN.dp))

    if (contentDetails?.genres?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_release_date_label)
        )
        contentDetails.genres.forEach {
            DetailDescriptionBody(it?.name.orEmpty())
        }
    }

    Spacer(modifier = Modifier.height(UiConstants.EXTRA_MARGIN.dp))

    if (contentDetails?.runtime != null) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_runtime_label)
        )
        DetailDescriptionBody(contentDetails.runtime.stringFormat())
    }
}

@Composable
fun DetailDescriptionLabel(
    labelText: String
) {
    Text(
        text = labelText,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun DetailDescriptionBody(
    bodyText: String
) {
    Text(
        text = bodyText,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.surface
    )
}