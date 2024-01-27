package com.projects.moviemanager.features.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.SetStatusBarColor
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.ui.util.UiConstants.SEARCH_CARDS_WIDTH
import com.projects.moviemanager.common.ui.util.calculateCardsPerRow
import com.projects.moviemanager.common.ui.util.dpToPx
import com.projects.moviemanager.common.ui.util.pxToDp
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.features.search.events.SearchEvent
import com.projects.moviemanager.features.search.ui.components.SearchBar
import com.projects.moviemanager.features.search.ui.components.SearchFiltersRow
import com.projects.moviemanager.features.search.ui.components.SearchTypeFilterItem
import com.projects.moviemanager.util.Constants.BASE_300_IMAGE_URL

@Composable
fun Search(
    goToDetails: (Int, MediaType) -> Unit
) {
    Search(
        viewModel = hiltViewModel(),
        goToDetails = goToDetails
    )
}

@Composable
private fun Search(
    viewModel: SearchViewModel,
    goToDetails: (Int, MediaType) -> Unit
) {
    val searchResults = viewModel.searchResult.collectAsLazyPagingItems()
    val searchTypeSelected by viewModel.searchFilterSelected

    val onFilterTypeSelected: (SearchTypeFilterItem) -> Unit = {
        viewModel.onEvent(
            SearchEvent.FilterTypeSelected(it)
        )
    }

    SetStatusBarColor()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            viewModel = viewModel
        )
        if (
            viewModel.searchQuery.value.isNotEmpty()
        ) {
            SearchFiltersRow(
                searchTypeSelected = searchTypeSelected,
                onFilterTypeSelected = onFilterTypeSelected
            )
        }

        when {
            searchResults.loadState.refresh is LoadState.Loading &&
                viewModel.searchQuery.value.isNotEmpty() -> {
                SearchLoadingIndicator()
            }
            searchResults.loadState.refresh is LoadState.Error -> {
                // Handle search error
            }
            else -> {
                SearchBody(searchResults, goToDetails)
            }
        }
    }
}

@Composable
private fun SearchBody(
    searchResults: LazyPagingItems<GenericSearchContent>,
    goToDetails: (Int, MediaType) -> Unit
) {
    val density = LocalDensity.current
    val screenWidth = density.run { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }
    val spacing = density.run { DEFAULT_PADDING.dp.roundToPx() }
    val minCardSize = pxToDp(SEARCH_CARDS_WIDTH, density)

    val (numCardsPerRow, adjustedCardSize) = calculateCardsPerRow(
        screenWidth,
        dpToPx(minCardSize, density),
        spacing,
        density
    )

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(numCardsPerRow),
        horizontalArrangement = Arrangement.Center
    ) {
        items(searchResults.itemCount) { index ->
            val item = searchResults[index]
            item?.let {
                SearchResultCard(
                    item = item,
                    adjustedCardSize = adjustedCardSize,
                    goToDetails = goToDetails
                )
            }
        }
    }
}

@Composable
private fun SearchResultCard(
    item: GenericSearchContent,
    adjustedCardSize: Dp,
    goToDetails: (Int, MediaType) -> Unit
) {
    val fullImageUrl = BASE_300_IMAGE_URL + item.posterPath
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = {
                    goToDetails(item.id, item.mediaType)
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium),
            imageUrl = fullImageUrl,
            widthDp = adjustedCardSize,
            heightDp = adjustedCardSize * POSTER_ASPECT_RATIO_MULTIPLY
        )
        Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
    }
}

@Composable
private fun SearchLoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.weight(0.7f))
    }
}
