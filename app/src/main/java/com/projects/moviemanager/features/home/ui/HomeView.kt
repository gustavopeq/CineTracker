package com.projects.moviemanager.features.home.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.ContentCard
import com.projects.moviemanager.common.ui.components.DimensionSubcomposeLayout
import com.projects.moviemanager.common.ui.util.UiConstants.CAROUSEL_CARDS_WIDTH
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.HOME_BACKGROUND_OFFSET_PERCENT
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.features.home.ui.components.featured.FeaturedBackgroundImage
import com.projects.moviemanager.features.home.ui.components.featured.FeaturedInfo
import com.projects.moviemanager.util.Constants.BASE_ORIGINAL_IMAGE_URL
import com.projects.moviemanager.util.removeParentPadding

@Composable
fun Home(
    goToDetails: (Int, MediaType) -> Unit
) {
    Home(
        viewModel = hiltViewModel(),
        goToDetails = goToDetails
    )
}

@Composable
private fun Home(
    viewModel: HomeViewModel,
    goToDetails: (Int, MediaType) -> Unit
) {
    val trendingMulti by viewModel.trendingMulti.collectAsState()
    val myWatchlist by viewModel.myWatchlist.collectAsState()
    val localDensity = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        if (trendingMulti.isNotEmpty()) {
            val homePosterUrl = BASE_ORIGINAL_IMAGE_URL + trendingMulti[0].posterPath

            DimensionSubcomposeLayout(
                mainContent = { FeaturedBackgroundImage(imageUrl = homePosterUrl) },
                dependentContent = { size ->
                    val mainBgOffset = localDensity.run { size.height.toDp() }

                    HomeBody(mainBgOffset, trendingMulti, myWatchlist, goToDetails)
                }
            )
        }
    }
}

@Composable
private fun HomeBody(
    mainBgOffset: Dp,
    trendingMulti: List<GenericSearchContent>,
    myWatchlist: List<GenericSearchContent>,
    goToDetails: (Int, MediaType) -> Unit
) {
    val secondaryTrendingItems = trendingMulti.drop(1)
    LazyColumn {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(mainBgOffset * HOME_BACKGROUND_OFFSET_PERCENT)
            )
        }
        item {
            FeaturedInfo(
                mainContent = trendingMulti[0],
                goToDetails = goToDetails
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                ClassicCarousel(
                    carouselHeaderRes = R.string.trending_today_header,
                    itemList = secondaryTrendingItems,
                    goToDetails = goToDetails
                )
                Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
                ClassicCarousel(
                    carouselHeaderRes = R.string.home_my_watchlist_header,
                    itemList = myWatchlist,
                    goToDetails = goToDetails
                )
            }
        }
    }
}

@Composable
private fun ClassicCarousel(
    @StringRes carouselHeaderRes: Int,
    itemList: List<GenericSearchContent>,
    goToDetails: (Int, MediaType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = DEFAULT_MARGIN.dp)
    ) {
        Text(
            text = stringResource(id = carouselHeaderRes).uppercase(),
            style = MaterialTheme.typography.headlineMedium
        )
        LazyRow(
            modifier = Modifier.removeParentPadding(DEFAULT_MARGIN.dp)
        ) {
            item {
                Spacer(modifier = Modifier.width(DEFAULT_MARGIN.dp))
            }
            items(itemList) { item ->
                ContentCard(
                    cardWidth = CAROUSEL_CARDS_WIDTH.dp,
                    imageUrl = item.posterPath,
                    title = item.name,
                    rating = item.rating,
                    goToDetails = {
                        goToDetails(item.id, item.mediaType)
                    }
                )
            }
        }
    }
}
