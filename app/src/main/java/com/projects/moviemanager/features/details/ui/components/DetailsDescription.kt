package com.projects.moviemanager.features.details.ui.components

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.GradientDirections
import com.projects.moviemanager.common.ui.components.RatingComponent
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.MovieDetailsInfo
import com.projects.moviemanager.domain.models.content.PersonDetailsInfo
import com.projects.moviemanager.domain.models.content.ShowDetailsInfo
import com.projects.moviemanager.features.details.util.stringFormat
import com.projects.moviemanager.network.response.content.common.ContentGenre
import com.projects.moviemanager.network.response.content.common.ProductionCountry
import com.projects.moviemanager.util.formatDate

@Composable
fun DetailsDescriptionHeader(
    contentDetails: DetailedMediaInfo?,
    updateHeaderPosition: (Float) -> Unit
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
                    updateHeaderPosition(it.positionInWindow().y)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DEFAULT_MARGIN.dp)
        ) {
            Text(
                text = "${contentDetails?.title}",
                style = MaterialTheme.typography.displayMedium
            )
            when (contentDetails) {
                is MovieDetailsInfo -> RatingComponent(rating = contentDetails.voteAverage)
                is ShowDetailsInfo -> RatingComponent(rating = contentDetails.voteAverage)
                else -> {}
            }
        }
    }
}

@Composable
fun DetailsDescriptionBody(
    contentDetails: DetailedMediaInfo
) {
    val context = LocalContext.current
    Text(
        text = contentDetails.overview,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )

    Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))

    when (contentDetails) {
        is MovieDetailsInfo -> {
            ProductionCountriesInfo(contentDetails.productionCountries)
            ReleaseDateInfo(contentDetails.releaseDate)
            GenresInfo(contentDetails.genres)
            RuntimeInfo(contentDetails.runtime)
        }
        is ShowDetailsInfo -> {
            ProductionCountriesInfo(contentDetails.productionCountries)
            GenresInfo(contentDetails.genres)
        }
        is PersonDetailsInfo -> {
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
