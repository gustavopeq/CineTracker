package com.projects.moviemanager.features.home.ui.components.featured

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.cards.DefaultContentCard
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.domain.models.content.GenericContent
import com.projects.moviemanager.util.removeParentPadding

@Composable
fun TrendingCarousel(
    trendingItems: List<GenericContent>,
    currentScreenWidth: Int,
    goToDetails: (Int, MediaType) -> Unit
) {
    ClassicCarousel(
        carouselHeaderRes = R.string.trending_today_header,
        itemList = trendingItems,
        currentScreenWidth = currentScreenWidth,
        goToDetails = goToDetails
    ) { item, goToDetails ->
        DefaultContentCard(
            cardWidth = UiConstants.CAROUSEL_CARDS_WIDTH.dp,
            imageUrl = item.posterPath,
            title = item.name,
            rating = item.rating,
            goToDetails = {
                goToDetails(item.id, item.mediaType)
            }
        )
    }
}

@Composable
fun ClassicCarousel(
    @StringRes carouselHeaderRes: Int,
    itemList: List<GenericContent>,
    currentScreenWidth: Int,
    itemSizeDp: Dp = UiConstants.CAROUSEL_CARDS_WIDTH.dp,
    goToDetails: (Int, MediaType) -> Unit,
    headerAdditionalAction: @Composable () -> Unit = {},
    contentCard: @Composable (GenericContent, (Int, MediaType) -> Unit) -> Unit
) {
    val cardsCountInScreen = currentScreenWidth / itemSizeDp.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = UiConstants.DEFAULT_MARGIN.dp, end = UiConstants.SMALL_MARGIN.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = carouselHeaderRes).uppercase(),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            headerAdditionalAction()
        }

        LazyRow(
            modifier = Modifier
                .removeParentPadding(UiConstants.DEFAULT_MARGIN.dp)
        ) {
            if (itemList.size >= cardsCountInScreen) {
                item {
                    Spacer(modifier = Modifier.width(UiConstants.DEFAULT_MARGIN.dp))
                }
            }
            items(itemList) { item ->
                contentCard(item, goToDetails)
                if (item == itemList.lastOrNull()) {
                    Spacer(modifier = Modifier.width(UiConstants.DEFAULT_MARGIN.dp))
                }
            }
        }
    }
}