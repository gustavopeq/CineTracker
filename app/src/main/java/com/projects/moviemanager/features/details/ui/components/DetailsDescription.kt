package com.projects.moviemanager.features.details.ui.components

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.content.DetailedContent
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.GradientDirections
import com.projects.moviemanager.common.ui.components.RatingComponent
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.DETAILS_OVERVIEW_MAX_LINES
import com.projects.moviemanager.common.util.formatDate
import com.projects.moviemanager.features.details.util.stringFormat
import com.projects.moviemanager.network.models.content.common.ContentGenre
import com.projects.moviemanager.network.models.content.common.ProductionCountry

@Composable
fun DetailsDescriptionHeader(
    contentDetails: DetailedContent?,
    updateTitlePosition: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .classicVerticalGradientBrush(
                direction = GradientDirections.UP
            )
    ) {
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .onGloballyPositioned {
                    val titlePosition = it.positionInWindow().y
                    updateTitlePosition(titlePosition)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DEFAULT_MARGIN.dp)
        ) {
            Text(
                text = "${contentDetails?.name}",
                style = MaterialTheme.typography.displayMedium
            )
            when (contentDetails?.mediaType) {
                MediaType.MOVIE, MediaType.SHOW -> RatingComponent(rating = contentDetails.rating)
                else -> {}
            }
        }
    }
}

@Composable
fun DetailsDescriptionBody(
    contentDetails: DetailedContent
) {
    val context = LocalContext.current
    OverviewInfo(contentDetails)

    Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))

    when (contentDetails.mediaType) {
        MediaType.MOVIE -> {
            ProductionCountriesInfo(contentDetails.productionCountries)
            ReleaseDateInfo(contentDetails.releaseDate)
            GenresInfo(contentDetails.genres)
            RuntimeInfo(contentDetails.runtime)
        }
        MediaType.SHOW -> {
            ProductionCountriesInfo(contentDetails.productionCountries)
            GenresInfo(contentDetails.genres)
        }
        MediaType.PERSON -> {
            BornDeathInfo(
                labelRes = R.string.person_details_born_label,
                bodyText = contentDetails.birthday,
                context = context
            )
            BornDeathInfo(
                labelRes = R.string.person_details_death_label,
                bodyText = contentDetails.deathday,
                context = context
            )
            BornInInfo(contentDetails.placeOfBirth)
        }

        else -> {}
    }
}

@Composable
private fun OverviewInfo(contentDetails: DetailedContent) {
    val text = contentDetails.overview
    var isExpanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }
    val textStyle = MaterialTheme.typography.bodyMedium

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = isOverflowing || isExpanded,
                onClick = {
                    isExpanded = !isExpanded
                }
            )
    ) {
        Text(
            text = text,
            style = textStyle,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = if (isExpanded) Int.MAX_VALUE else DETAILS_OVERVIEW_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                isOverflowing = textLayoutResult.hasVisualOverflow
            }
        )
        if (isOverflowing && !isExpanded) {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = stringResource(id = R.string.read_more_text),
                style = textStyle,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun RuntimeInfo(runtime: Int?) {
    if (runtime != null && runtime != 0) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_runtime_label)
        )
        DetailDescriptionBody(runtime.stringFormat())
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun GenresInfo(genres: List<ContentGenre?>?) {
    if (genres?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_genres_label)
        )
        val formattedGenres = genres.map { it?.name }.joinToString(", ")
        DetailDescriptionBody(bodyText = formattedGenres)
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun ReleaseDateInfo(releaseDate: String?) {
    if (releaseDate?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_release_date_label)
        )
        val formattedDate = releaseDate.formatDate(LocalContext.current)
        DetailDescriptionBody(formattedDate)
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun ProductionCountriesInfo(productionCountry: List<ProductionCountry?>?) {
    if (productionCountry?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_production_country_title)
        )

        productionCountry.forEach {
            DetailDescriptionBody(it?.name.orEmpty())
        }
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun BornDeathInfo(
    @StringRes labelRes: Int,
    bodyText: String?,
    context: Context
) {
    if (bodyText?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = labelRes)
        )
        DetailDescriptionBody(bodyText.formatDate(context))
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun BornInInfo(
    bornIn: String?
) {
    if (bornIn?.isNotEmpty() == true) {
        DetailDescriptionLabel(
            stringResource(id = R.string.person_details_born_in_label)
        )
        DetailDescriptionBody(bornIn)
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
fun DetailDescriptionLabel(
    labelText: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        text = labelText,
        style = textStyle,
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
