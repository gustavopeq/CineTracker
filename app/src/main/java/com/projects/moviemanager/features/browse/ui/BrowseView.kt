package com.projects.moviemanager.features.browse.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.domain.models.util.SortTypeItem
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.ComponentPlaceholder
import com.projects.moviemanager.common.ui.components.card.DefaultContentCard
import com.projects.moviemanager.common.ui.theme.RoundCornerShapes
import com.projects.moviemanager.common.util.UiConstants.BROWSE_CARD_PADDING_HORIZONTAL
import com.projects.moviemanager.common.util.UiConstants.BROWSE_CARD_PADDING_VERTICAL
import com.projects.moviemanager.common.util.UiConstants.BROWSE_MIN_CARD_WIDTH
import com.projects.moviemanager.common.util.UiConstants.BROWSE_SCAFFOLD_HEIGHT_OFFSET
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.common.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.common.util.calculateCardsPerRow
import com.projects.moviemanager.common.util.dpToPx
import com.projects.moviemanager.common.util.pxToDp
import com.projects.moviemanager.features.browse.BrowseScreen
import com.projects.moviemanager.features.browse.events.BrowseEvent
import com.projects.moviemanager.features.browse.ui.components.CollapsingTabRow

@Composable
fun Browse(
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
) {
    Browse(
        viewModel = hiltViewModel(),
        mainViewModel = hiltViewModel(),
        goToDetails = goToDetails,
        goToErrorScreen = goToErrorScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Browse(
    viewModel: BrowseViewModel,
    mainViewModel: MainViewModel,
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit

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

    LaunchedEffect(Unit) {
        mainViewModel.updateCurrentScreen(BrowseScreen.route())
    }

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
                            goToDetails = goToDetails,
                            goToErrorScreen = goToErrorScreen
                        )
                    }
                    1 -> {
                        BrowseBody(
                            viewModel = viewModel,
                            mediaType = MediaType.SHOW,
                            pagingData = listOfShows,
                            sortTypeItem = showSortType,
                            goToDetails = goToDetails,
                            goToErrorScreen = goToErrorScreen
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
    pagingData: LazyPagingItems<GenericContent>,
    sortTypeItem: SortTypeItem,
    goToDetails: (Int, MediaType) -> Unit,
    goToErrorScreen: () -> Unit
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
                viewModel.onEvent(BrowseEvent.OnError)
                goToErrorScreen()
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(numCardsPerRow),
                    modifier = Modifier.padding(horizontal = SMALL_MARGIN.dp)
                ) {
                    items(pagingData.itemCount) { index ->
                        val content = pagingData[index]
                        content?.let {
                            DefaultContentCard(
                                modifier = Modifier
                                    .width(adjustedCardSize)
                                    .padding(
                                        horizontal = BROWSE_CARD_PADDING_HORIZONTAL.dp,
                                        vertical = BROWSE_CARD_PADDING_VERTICAL.dp
                                    ),
                                cardWidth = adjustedCardSize,
                                imageUrl = content.posterPath,
                                title = content.name,
                                rating = content.rating,
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
        items(numberOfCards * numberOfCards) {
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
