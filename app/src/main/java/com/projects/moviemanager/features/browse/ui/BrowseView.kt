package com.projects.moviemanager.features.browse.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.theme.RoundCornerShapes
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.ComponentPlaceholder
import com.projects.moviemanager.common.ui.components.card.DefaultContentCard
import com.projects.moviemanager.common.domain.SortTypeItem
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_CARD_PADDING_HORIZONTAL
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_CARD_PADDING_VERTICAL
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_MIN_CARD_WIDTH
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_SCAFFOLD_HEIGHT_OFFSET
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.common.ui.util.calculateCardsPerRow
import com.projects.moviemanager.common.ui.util.dpToPx
import com.projects.moviemanager.common.ui.util.pxToDp
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.MovieDetailsInfo
import com.projects.moviemanager.domain.models.content.ShowDetailsInfo
import com.projects.moviemanager.features.browse.events.BrowseEvent
import com.projects.moviemanager.features.browse.ui.components.CollapsingTabRow
import timber.log.Timber

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
    pagingData: LazyPagingItems<DetailedMediaInfo>,
    sortTypeItem: SortTypeItem,
    goToDetails: (Int, MediaType) -> Unit
) {
    LaunchedEffect(sortTypeItem) {
        viewModel.onEvent(BrowseEvent.UpdateSortType(sortTypeItem, mediaType))
    }

    val density = LocalDensity.current
    val screenWidth = density.run { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }
    val spacing = density.run { DEFAULT_MARGIN.dp.roundToPx() }
    val minCardSize = pxToDp(BROWSE_MIN_CARD_WIDTH, density)

    val (numCardsPerRow, adjustedCardSize) = calculateCardsPerRow(
        screenWidth,
        dpToPx(minCardSize, density),
        spacing,
        density
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (pagingData.loadState.refresh) {
            is LoadState.Loading -> {
                BrowseBodyPlaceholder(
                    numberOfCards = numCardsPerRow,
                    cardWidth = adjustedCardSize
                )
            }
            is LoadState.Error -> {
                Text(
                    text = stringResource(id = R.string.generic_error_message),
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
                    columns = GridCells.Fixed(numCardsPerRow),
                    modifier = Modifier.padding(horizontal = SMALL_MARGIN.dp)
                ) {
                    items(pagingData.itemCount) { index ->
                        val content = pagingData[index]
                        content?.let {
                            val contentVoteAverage = when (content) {
                                is MovieDetailsInfo -> content.voteAverage
                                is ShowDetailsInfo -> content.voteAverage
                                else -> {
                                    Timber.tag("print").d("content else")
                                    null
                                }
                            }
                            DefaultContentCard(
                                modifier = Modifier
                                    .width(adjustedCardSize)
                                    .padding(
                                        horizontal = BROWSE_CARD_PADDING_HORIZONTAL.dp,
                                        vertical = BROWSE_CARD_PADDING_VERTICAL.dp
                                    ),
                                cardWidth = adjustedCardSize,
                                imageUrl = content.poster_path,
                                title = content.title,
                                rating = contentVoteAverage,
                                goToDetails = { goToDetails(content.id, content.mediaType) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BrowseBodyPlaceholder(
    numberOfCards: Int,
    cardWidth: Dp
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(numberOfCards),
        modifier = Modifier.padding(horizontal = SMALL_MARGIN.dp)
    ) {
        items(numberOfCards * 2) {
            Column(
                modifier = Modifier
                    .width(
                        width = cardWidth
                    )
                    .padding(
                        horizontal = BROWSE_CARD_PADDING_HORIZONTAL.dp,
                        vertical = BROWSE_CARD_PADDING_VERTICAL.dp
                    )
            ) {
                ComponentPlaceholder(
                    modifier = Modifier
                        .width(cardWidth)
                        .height(cardWidth * POSTER_ASPECT_RATIO_MULTIPLY)
                        .clip(RoundCornerShapes.small)
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
                ComponentPlaceholder(
                    modifier = Modifier
                        .width(cardWidth)
                        .height(50.dp)
                        .clip(RoundCornerShapes.extraSmall)
                )
            }
        }
    }
}
