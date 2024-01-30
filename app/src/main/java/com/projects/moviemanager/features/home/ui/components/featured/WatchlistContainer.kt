package com.projects.moviemanager.features.home.ui.components.featured

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.cards.ImageContentCard
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.domain.models.content.GenericContent
import com.projects.moviemanager.features.home.ui.ClassicCarousel

@Composable
fun WatchlistCarousel(
    watchlist: List<GenericContent>,
    currentScreenWidth: Int,
    goToDetails: (Int, MediaType) -> Unit,
    goToWatchlist: () -> Unit
) {
    ClassicCarousel(
        carouselHeaderRes = R.string.home_my_watchlist_header,
        itemList = watchlist,
        currentScreenWidth = currentScreenWidth,
        goToDetails = goToDetails,
        headerAdditionalAction = {
            CarouselSeeAllOption(goToWatchlist)
        }
    ) { item, goToDetails ->
        ImageContentCard(
            modifier = Modifier.padding(
                horizontal = UiConstants.BROWSE_CARD_PADDING_HORIZONTAL.dp,
                vertical = UiConstants.BROWSE_CARD_PADDING_VERTICAL.dp
            ),
            item = item,
            adjustedCardSize = UiConstants.CAROUSEL_CARDS_WIDTH.dp,
            goToDetails = goToDetails
        )
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