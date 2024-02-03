package com.projects.moviemanager.features.home.ui.components.carousel

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.card.DefaultContentCard
import com.projects.moviemanager.common.ui.util.UiConstants.CAROUSEL_CARDS_WIDTH
import com.projects.moviemanager.common.ui.util.UiConstants.CAROUSEL_RATING_STAR_SIZE
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.util.removeParentPadding

@Composable
fun TrendingCarousel(
    trendingItems: List<GenericContent>,
    currentScreenWidth: Int,
    goToDetails: (Int, MediaType) -> Unit
) {
    if (trendingItems.isNotEmpty()) {
        ClassicCarousel(
            carouselHeaderRes = R.string.trending_today_header,
            itemList = trendingItems,
            currentScreenWidth = currentScreenWidth,
            goToDetails = goToDetails
        ) { item, goToDetails ->
            DefaultContentCard(
                modifier = Modifier.padding(
                    top = DEFAULT_PADDING.dp,
                    bottom = DEFAULT_PADDING.dp,
                    end = DEFAULT_PADDING.dp
                ),
                cardWidth = CAROUSEL_CARDS_WIDTH.dp,
                imageUrl = item.posterPath,
                title = item.name,
                rating = item.rating,
                textStyle = MaterialTheme.typography.bodyMedium,
                ratingIconSize = CAROUSEL_RATING_STAR_SIZE,
                goToDetails = {
                    goToDetails(item.id, item.mediaType)
                }
            )
        }

        Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
    }
}

@Composable
fun ClassicCarousel(
    @StringRes carouselHeaderRes: Int,
    itemList: List<GenericContent>,
    currentScreenWidth: Int,
    itemSizeDp: Dp = CAROUSEL_CARDS_WIDTH.dp,
    goToDetails: (Int, MediaType) -> Unit,
    headerAdditionalAction: @Composable () -> Unit = {},
    contentCard: @Composable (GenericContent, (Int, MediaType) -> Unit) -> Unit
) {
    val cardsCountInScreen = currentScreenWidth / itemSizeDp.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = DEFAULT_MARGIN.dp, end = SMALL_MARGIN.dp)
    ) {
        CarouselHeaderRow(carouselHeaderRes, headerAdditionalAction)

        LazyRow(
            modifier = Modifier.removeParentPadding(DEFAULT_MARGIN.dp)
        ) {
            if (itemList.size >= cardsCountInScreen) {
                item {
                    Spacer(modifier = Modifier.width(DEFAULT_MARGIN.dp))
                }
            }
            items(itemList) { item ->
                contentCard(item, goToDetails)
                if (item == itemList.lastOrNull()) {
                    Spacer(modifier = Modifier.width(DEFAULT_MARGIN.dp))
                }
            }
        }
    }
}

@Composable
fun CarouselHeaderRow(
    carouselHeaderRes: Int,
    headerAdditionalAction: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CarouselHeader(carouselHeaderRes)
        Spacer(modifier = Modifier.weight(1f))
        headerAdditionalAction()
    }
}

@Composable
fun CarouselHeader(carouselHeaderRes: Int) {
    Text(
        text = stringResource(id = carouselHeaderRes).uppercase(),
        style = MaterialTheme.typography.headlineMedium
    )
}