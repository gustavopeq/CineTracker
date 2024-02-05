package com.projects.moviemanager.features.home.ui.components.carousel

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.card.ImageContentCard
import com.projects.moviemanager.common.util.UiConstants.CAROUSEL_CARDS_WIDTH
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.util.UiConstants.LARGE_PADDING
import com.projects.moviemanager.common.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.common.domain.models.content.GenericContent

@Composable
fun WatchlistCarousel(
    watchlist: List<GenericContent>,
    currentScreenWidth: Int,
    goToDetails: (Int, MediaType) -> Unit,
    goToWatchlist: () -> Unit
) {
    val carouselHeader = R.string.home_my_watchlist_header

    if (watchlist.isNotEmpty()) {
        ClassicCarousel(
            carouselHeaderRes = carouselHeader,
            itemList = watchlist,
            currentScreenWidth = currentScreenWidth,
            goToDetails = goToDetails,
            headerAdditionalAction = {
                CarouselSeeAllOption(goToWatchlist)
            }
        ) { item, goToDetails ->
            ImageContentCard(
                modifier = Modifier.padding(
                    top = DEFAULT_PADDING.dp,
                    bottom = DEFAULT_PADDING.dp,
                    end = DEFAULT_PADDING.dp
                ),
                item = item,
                adjustedCardSize = CAROUSEL_CARDS_WIDTH.dp,
                goToDetails = goToDetails
            )
        }
    } else {
        WatchlistEmptyCarousel(
            carouselHeaderRes = carouselHeader,
            headerAdditionalAction = {
                CarouselSeeAllOption(goToWatchlist)
            }
        )
    }

    Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
}

@Composable
private fun WatchlistEmptyCarousel(
    @StringRes carouselHeaderRes: Int,
    headerAdditionalAction: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(DEFAULT_MARGIN.dp)
            .fillMaxWidth()
    ) {
        CarouselHeaderRow(
            carouselHeaderRes = carouselHeaderRes,
            headerAdditionalAction = headerAdditionalAction
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.empty_list_header),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
            Text(
                text = stringResource(id = R.string.watchlist_carousel_empty_message),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
private fun CarouselSeeAllOption(goToWatchlist: () -> Unit) {
    Row(
        modifier = Modifier.clickable(
            onClick = goToWatchlist
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.carousel_see_all_button),
            style = MaterialTheme.typography.titleSmall
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null
        )
    }
}
