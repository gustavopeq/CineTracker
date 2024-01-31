package com.projects.moviemanager.features.home.ui.components.featured

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_CARD_DEFAULT_ELEVATION
import com.projects.moviemanager.common.ui.util.UiConstants.PERSON_FEATURED_IMAGE_WIDTH
import com.projects.moviemanager.domain.models.person.PersonDetails
import com.projects.moviemanager.util.Constants

@Composable
fun PersonFeaturedInfo(
    trendingPerson: PersonDetails?,
    goToDetails: (Int, MediaType) -> Unit
) {
    trendingPerson?.let {
        val fullImagePath = Constants.BASE_ORIGINAL_IMAGE_URL + trendingPerson.posterPath
        val imageWidth = PERSON_FEATURED_IMAGE_WIDTH.dp
        val imageHeight = imageWidth * UiConstants.POSTER_ASPECT_RATIO_MULTIPLY

        Box(
            modifier = Modifier.padding(horizontal = UiConstants.DEFAULT_MARGIN.dp)
        ) {
            Card(
                modifier = Modifier
                    .clickable(
                        onClick = { goToDetails(trendingPerson.id, trendingPerson.mediaType) }
                    )
                    .height(imageHeight),
                colors = CardDefaults.cardColors(
                    containerColor = MainBarGreyColor
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = BROWSE_CARD_DEFAULT_ELEVATION.dp
                )
            ) {
                Row {
                    Column(
                        modifier = Modifier
                            .padding(UiConstants.DEFAULT_PADDING.dp)
                            .weight(1f)
                    ) {
                        HomeCardTitle(title = trendingPerson.title)
                        Spacer(modifier = Modifier.weight(1f))

                        CardHeader(
                            headerRes = R.string.person_featured_card_role_header
                        )
                        Spacer(modifier = Modifier.height(UiConstants.SMALL_PADDING.dp))
                        Text(
                            text = trendingPerson.knownForDepartment.orEmpty(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(UiConstants.DEFAULT_PADDING.dp))

                        CardHeader(
                            headerRes = R.string.person_featured_card_known_for_header
                        )
                        Spacer(modifier = Modifier.height(UiConstants.SMALL_PADDING.dp))
                        trendingPerson.knownFor.forEach {
                            it.title?.let { title ->
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    NetworkImage(
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                topEnd = UiConstants.CARD_ROUND_CORNER.dp,
                                bottomEnd = UiConstants.CARD_ROUND_CORNER.dp
                            )
                        ),
                        imageUrl = fullImagePath,
                        widthDp = imageWidth,
                        heightDp = imageHeight
                    )
                }
            }
        }
    }
}

@Composable
fun HomeCardTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
private fun CardHeader(
    @StringRes headerRes: Int
) {
    Text(
        text = stringResource(id = headerRes),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.surface
    )
}