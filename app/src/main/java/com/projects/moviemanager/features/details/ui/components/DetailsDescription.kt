package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.projects.moviemanager.common.domain.models.content.StreamProvider
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.GradientDirections
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.RatingComponent
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.util.Constants.BASE_ORIGINAL_IMAGE_URL
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.util.UiConstants.DETAILS_OVERVIEW_MAX_LINES
import com.projects.moviemanager.common.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.common.util.UiConstants.STREAM_PROVIDER_ICON_SIZE
import com.projects.moviemanager.common.util.formatDate
import com.projects.moviemanager.features.details.util.formatRuntime
import com.projects.moviemanager.features.details.util.isValidValue
import com.projects.moviemanager.features.details.util.toFormattedCurrency
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
    if (contentDetails.overview.isNotEmpty()) {
        OverviewInfo(contentDetails)
    }

    when (contentDetails.mediaType) {
        MediaType.MOVIE -> {
            DateInfo(
                header = stringResource(id = R.string.movie_details_release_date_label),
                date = contentDetails.releaseDate
            )

            GenresInfo(contentDetails.genres)

            RuntimeInfo(contentDetails.runtime)

            ProductionCountriesInfo(contentDetails.productionCountries)

            FinanceInfo(
                header = stringResource(id = R.string.movie_details_budget_label),
                value = contentDetails.budget
            )

            FinanceInfo(
                header = stringResource(id = R.string.movie_details_revenue_label),
                value = contentDetails.revenue
            )

            StreamProviderInfo(
                streamProviders = contentDetails.streamProviders
            )
        }
        MediaType.SHOW -> {
            DateInfo(
                header = stringResource(id = R.string.show_details_first_air_date_label),
                date = contentDetails.firstAirDate
            )

            DateInfo(
                header = stringResource(id = R.string.show_details_last_air_date_label),
                date = contentDetails.lastAirDate
            )

            GenresInfo(contentDetails.genres)

            ShowDurationInfo(
                seasonNumber = contentDetails.numberOfSeasons,
                episodeNumber = contentDetails.numberOfEpisodes
            )

            ProductionCountriesInfo(contentDetails.productionCountries)

            StreamProviderInfo(
                streamProviders = contentDetails.streamProviders
            )
        }
        MediaType.PERSON -> {
            DateInfo(
                header = stringResource(id = R.string.person_details_born_label),
                date = contentDetails.birthday
            )

            DateInfo(
                header = stringResource(id = R.string.person_details_death_label),
                date = contentDetails.deathday
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
        } else {
            Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
        }
    }
}

@Composable
private fun RuntimeInfo(runtime: Int) {
    if (runtime > 0) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_runtime_label)
        )
        DetailDescriptionBody(runtime.formatRuntime())
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun GenresInfo(genres: List<ContentGenre?>) {
    if (genres.isNotEmpty()) {
        DetailDescriptionLabel(
            stringResource(id = R.string.movie_details_genres_label)
        )
        val formattedGenres = genres.map { it?.name }.joinToString(", ")
        DetailDescriptionBody(bodyText = formattedGenres)
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun ProductionCountriesInfo(productionCountry: List<ProductionCountry?>) {
    if (productionCountry.isNotEmpty()) {
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
private fun FinanceInfo(
    header: String,
    value: Long
) {
    if (value.isValidValue()) {
        DetailDescriptionLabel(header)
        DetailDescriptionBody(
            bodyText = value.toFormattedCurrency()
        )
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun DateInfo(
    header: String,
    date: String
) {
    if (date.isNotEmpty()) {
        DetailDescriptionLabel(header)
        val formattedDate = date.formatDate(LocalContext.current)
        DetailDescriptionBody(formattedDate)
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun ShowDurationInfo(
    seasonNumber: Int,
    episodeNumber: Int
) {
    if (seasonNumber > 0 && episodeNumber > 0) {
        DetailDescriptionLabel(
            stringResource(id = R.string.show_details_duration_label)
        )
        val resources = LocalContext.current.resources
        val seasonString = resources.getQuantityString(
            R.plurals.seasons,
            seasonNumber,
            seasonNumber
        )
        val episodeString = resources.getQuantityString(
            R.plurals.episodes,
            episodeNumber,
            episodeNumber
        )

        DetailDescriptionBody(bodyText = "$seasonString, $episodeString")
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun BornInInfo(
    bornIn: String
) {
    if (bornIn.isNotEmpty()) {
        DetailDescriptionLabel(
            stringResource(id = R.string.person_details_born_in_label)
        )
        DetailDescriptionBody(bornIn)
        Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
    }
}

@Composable
private fun StreamProviderInfo(
    streamProviders: List<StreamProvider>
) {
    if (streamProviders.isNotEmpty()) {
        DetailDescriptionLabel(
            stringResource(id = R.string.content_details_stream_label)
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
        LazyRow {
            items(streamProviders) { stream ->
                val fullImagePath = BASE_ORIGINAL_IMAGE_URL + stream.logoPath
                NetworkImage(
                    modifier = Modifier
                        .size(STREAM_PROVIDER_ICON_SIZE.dp)
                        .clip(MaterialTheme.shapes.medium),
                    imageUrl = fullImagePath
                )
                Spacer(modifier = Modifier.width(DEFAULT_PADDING.dp))
            }
        }
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
