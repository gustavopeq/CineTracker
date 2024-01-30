package com.projects.moviemanager.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.DimensionSubcomposeLayout
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.util.UiConstants.BACKDROP_ASPECT_RATIO
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.HOME_BACKGROUND_OFFSET_PERCENT
import com.projects.moviemanager.domain.models.content.GenericContent
import com.projects.moviemanager.features.home.ui.components.featured.FeaturedBackgroundImage
import com.projects.moviemanager.features.home.ui.components.featured.FeaturedInfo
import com.projects.moviemanager.features.home.ui.components.carousel.TrendingCarousel
import com.projects.moviemanager.features.home.ui.components.carousel.WatchlistCarousel
import com.projects.moviemanager.features.home.ui.components.featured.SecondaryFeaturedInfo
import com.projects.moviemanager.util.Constants.BASE_ORIGINAL_IMAGE_URL

@Composable
fun Home(
    goToDetails: (Int, MediaType) -> Unit,
    goToWatchlist: () -> Unit
) {
    Home(
        viewModel = hiltViewModel(),
        goToDetails = goToDetails,
        goToWatchlist = goToWatchlist
    )
}

@Composable
private fun Home(
    viewModel: HomeViewModel,
    goToDetails: (Int, MediaType) -> Unit,
    goToWatchlist: () -> Unit
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

                    HomeBody(mainBgOffset, trendingMulti, myWatchlist, goToDetails, goToWatchlist)
                }
            )
        }
    }
}

@Composable
private fun HomeBody(
    mainBgOffset: Dp,
    trendingMulti: List<GenericContent>,
    myWatchlist: List<GenericContent>,
    goToDetails: (Int, MediaType) -> Unit,
    goToWatchlist: () -> Unit
) {
    val currentScreenWidth = LocalConfiguration.current.screenWidthDp
    val featuredItem = trendingMulti[0]
    val secondaryTrendingItems = trendingMulti.drop(1)
    val secondaryFeaturedItem = trendingMulti.firstOrNull {
        when (featuredItem.mediaType) {
            MediaType.MOVIE -> it.mediaType == MediaType.SHOW
            else -> it.mediaType == MediaType.MOVIE
        }
    }

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
                featuredContent = featuredItem,
                goToDetails = goToDetails
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                TrendingCarousel(
                    trendingItems = secondaryTrendingItems,
                    currentScreenWidth = currentScreenWidth,
                    goToDetails = goToDetails
                )
                Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
                WatchlistCarousel(
                    watchlist = myWatchlist,
                    currentScreenWidth = currentScreenWidth,
                    goToDetails = goToDetails,
                    goToWatchlist = goToWatchlist
                )
                Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
                SecondaryFeaturedInfo(
                    featuredItem = secondaryFeaturedItem,
                    goToDetails = goToDetails
                )
                Spacer(modifier = Modifier.height(DEFAULT_MARGIN.dp))
            }
        }
    }
}
