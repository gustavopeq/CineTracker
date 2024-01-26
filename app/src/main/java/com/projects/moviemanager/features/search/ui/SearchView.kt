package com.projects.moviemanager.features.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.SetStatusBarColor
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.features.search.events.SearchEvent
import com.projects.moviemanager.features.search.ui.components.SearchBar
import com.projects.moviemanager.util.Constants.BASE_300_IMAGE_URL

@Composable
fun Search() {
    Search(
        viewModel = hiltViewModel()
    )
}

@Composable
private fun Search(
    viewModel: SearchViewModel
) {
    val searchResults = viewModel.searchResult.collectAsLazyPagingItems()
    SetStatusBarColor()

    val onFilterTypeSelected: (MediaType?) -> Unit = {
        viewModel.onEvent(
            SearchEvent.FilterTypeSelected(it)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            viewModel = viewModel
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { onFilterTypeSelected(null) }) {
                Text(text = "All")
            }
            Button(onClick = { onFilterTypeSelected(MediaType.MOVIE) }) {
                Text(text = "Movie")
            }
            Button(onClick = { onFilterTypeSelected(MediaType.SHOW) }) {
                Text(text = "Show")
            }
            Button(onClick = { onFilterTypeSelected(MediaType.PERSON) }) {
                Text(text = "People")
            }
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
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(100.dp),
                    horizontalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(vertical = SMALL_PADDING.dp)
                ) {
                    items(searchResults.itemCount) { index ->
                        val item = searchResults[index]
                        item?.let {
                            val fullImageUrl = BASE_300_IMAGE_URL + item.posterPath
                            val imageWidth = 100.dp
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                NetworkImage(
                                    imageUrl = fullImageUrl,
                                    widthDp = imageWidth,
                                    heightDp = imageWidth * POSTER_ASPECT_RATIO_MULTIPLY
                                )
                            }
                        }
                    }
                }
            }
        }
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
