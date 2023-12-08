package com.projects.moviemanager.compose.features.browse.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.projects.moviemanager.R
import com.projects.moviemanager.compose.common.MainViewModel
import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.compose.common.ui.components.ComponentPlaceholder
import com.projects.moviemanager.compose.common.ui.components.NetworkImage
import com.projects.moviemanager.compose.common.ui.components.RatingComponent
import com.projects.moviemanager.compose.common.ui.components.SortTypeItem
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_CARD_DEFAULT_ELEVATION
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_CARD_HEIGHT
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_CARD_IMAGE_HEIGHT
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_CARD_PADDING_HORIZONTAL
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_CARD_PADDING_VERTICAL
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_CARD_WIDTH
import com.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_SCAFFOLD_HEIGHT_OFFSET
import com.projects.moviemanager.compose.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.compose.common.ui.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.compose.features.browse.events.BrowseEvent
import com.projects.moviemanager.compose.features.browse.ui.components.CollapsingTabRow
import com.projects.moviemanager.compose.theme.MainBarGreyColor
import com.projects.moviemanager.compose.theme.RoundCornerShapes
import com.projects.moviemanager.domain.models.content.BaseMediaContent
import com.projects.moviemanager.util.Constants.BASE_500_IMAGE_URL

@Composable
fun Browse(
    goToDetails: (Int, MediaType) -> Unit
) {
    Browse(
        viewModel = hiltViewModel(),
        mainViewModel = hiltViewModel(),
        goToDetails = goToDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Browse(
    viewModel: BrowseViewModel,
    mainViewModel: MainViewModel,
    goToDetails: (Int, MediaType) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val currentMediaTypeSelected by mainViewModel.currentMediaTypeSelected.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = currentMediaTypeSelected.ordinal,
        pageCount = { 2 }
    )

    val listOfMovies = viewModel.moviePager.collectAsLazyPagingItems()
    val movieSortType by mainViewModel.movieSortType.collectAsState()

    val listOfShows = viewModel.showPager.collectAsLazyPagingItems()
    val showSortType by mainViewModel.showSortType.collectAsState()

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> mainViewModel.updateMediaType(MediaType.MOVIE)
            1 -> mainViewModel.updateMediaType(MediaType.SHOW)
        }
    }

    Scaffold(
        modifier = Modifier
            .offset(y = (-BROWSE_SCAFFOLD_HEIGHT_OFFSET).dp)
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .then(
                /* To account for the scaffold offset, we have to do this calculation to increase
                the scaffold height */
                Modifier.layout { measurable, constraints ->
                    val fixedContentConstraints = constraints.copy(
                        maxHeight = constraints.maxHeight +
                            BROWSE_SCAFFOLD_HEIGHT_OFFSET.dp.roundToPx()
                    )
                    val placeable = measurable.measure(fixedContentConstraints)

                    layout(
                        width = placeable.width,
                        height = placeable.height + BROWSE_SCAFFOLD_HEIGHT_OFFSET.dp.roundToPx()
                    ) {
                        placeable.placeRelative(
                            x = 0,
                            y = BROWSE_SCAFFOLD_HEIGHT_OFFSET.dp.roundToPx()
                        )
                    }
                }
            ),
        topBar = {
            CollapsingTabRow(
                scrollBehavior = scrollBehavior,
                viewModel = viewModel,
                pagerState = pagerState
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> {
                        BrowseBody(
                            viewModel = viewModel,
                            mediaType = MediaType.MOVIE,
                            pagingData = listOfMovies,
                            sortTypeItem = movieSortType,
                            goToDetails = goToDetails
                        )
                    }
                    1 -> {
                        BrowseBody(
                            viewModel = viewModel,
                            mediaType = MediaType.SHOW,
                            pagingData = listOfShows,
                            sortTypeItem = showSortType,
                            goToDetails = goToDetails
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BrowseBody(
    viewModel: BrowseViewModel,
    mediaType: MediaType,
    pagingData: LazyPagingItems<BaseMediaContent>,
    sortTypeItem: SortTypeItem,
    goToDetails: (Int, MediaType) -> Unit
) {
    LaunchedEffect(sortTypeItem) {
        viewModel.onEvent(BrowseEvent.UpdateSortType(sortTypeItem, mediaType))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (pagingData.loadState.refresh) {
            is LoadState.Loading -> {
                BrowseBodyPlaceholder()
            }
            is LoadState.Error -> {
                Text(
                    text = stringResource(id = R.string.common_error),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(BROWSE_CARD_WIDTH.dp),
                    modifier = Modifier.padding(horizontal = DEFAULT_MARGIN.dp)
                ) {
                    items(pagingData.itemCount) { index ->
                        val content = pagingData[index]
                        content?.let {
                            BrowseCard(
                                imageUrl = content.poster_path,
                                title = content.title,
                                rating = content.vote_average,
                                goToDetails = { goToDetails(content.id, content.mediaType) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrowseCard(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String?,
    rating: Double?,
    goToDetails: () -> Unit
) {
    val fullImageUrl = BASE_500_IMAGE_URL + imageUrl

    Card(
        modifier = modifier
            .padding(
                horizontal = BROWSE_CARD_PADDING_HORIZONTAL.dp,
                vertical = BROWSE_CARD_PADDING_VERTICAL.dp
            )
            .size(
                width = BROWSE_CARD_WIDTH.dp,
                height = BROWSE_CARD_HEIGHT.dp
            ),
        onClick = goToDetails,
        colors = CardDefaults.cardColors(
            containerColor = MainBarGreyColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = BROWSE_CARD_DEFAULT_ELEVATION.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = SMALL_PADDING.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            NetworkImage(
                imageUrl = fullImageUrl,
                modifier = Modifier.clip(RoundCornerShapes.small),
                widthDp = BROWSE_CARD_WIDTH.dp,
                heightDp = BROWSE_CARD_IMAGE_HEIGHT.dp
            )
            Spacer(modifier = Modifier.width(SMALL_PADDING.dp))
            Text(
                text = title.orEmpty(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            RatingComponent(rating = rating)
        }
    }
}

@Composable
private fun BrowseBodyPlaceholder() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(BROWSE_CARD_WIDTH.dp),
        modifier = Modifier.padding(horizontal = DEFAULT_MARGIN.dp)
    ) {
        items(6) {
            Column(
                modifier = Modifier
                    .size(
                        width = BROWSE_CARD_WIDTH.dp,
                        height = BROWSE_CARD_HEIGHT.dp + 16.dp
                    )
                    .padding(
                        horizontal = BROWSE_CARD_PADDING_HORIZONTAL.dp,
                        vertical = BROWSE_CARD_PADDING_VERTICAL.dp
                    )
            ) {
                ComponentPlaceholder(
                    widthDp = BROWSE_CARD_WIDTH.dp,
                    heightDp = BROWSE_CARD_IMAGE_HEIGHT.dp,
                    modifier = Modifier
                        .clip(RoundCornerShapes.small)
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
                ComponentPlaceholder(
                    widthDp = BROWSE_CARD_WIDTH.dp,
                    heightDp = 100.dp,
                    modifier = Modifier
                        .clip(RoundCornerShapes.extraSmall)
                )
            }
        }
    }
}
