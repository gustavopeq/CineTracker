package com.projects.moviemanager.common.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.cards.DefaultContentCard
import com.projects.moviemanager.common.ui.components.cards.PersonImages
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_CARD_PADDING_HORIZONTAL
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_CARD_PADDING_VERTICAL
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_MIN_CARD_WIDTH
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.calculateCardsPerRow
import com.projects.moviemanager.common.ui.util.dpToPx
import com.projects.moviemanager.common.ui.util.pxToDp
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.domain.models.person.PersonImage
import com.projects.moviemanager.util.removeParentPadding

@Composable
fun <T> GenericGrid(
    itemList: List<T>,
    maxCardsNumber: Int,
    displayItem: @Composable (T, Dp) -> Unit
) {
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

    Column(
        modifier = Modifier.fillMaxWidth().removeParentPadding(DEFAULT_MARGIN.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val filteredList = itemList.take(maxCardsNumber)
            .chunked(numCardsPerRow)

        filteredList.forEach { rowItems ->
            Row {
                rowItems.forEach { content ->
                    displayItem(content, adjustedCardSize)
                }
            }
        }
    }
}

@Composable
fun GridContentList(
    mediaContentList: List<MediaContent>,
    maxCardsNumber: Int? = null,
    openContentDetails: (Int, MediaType) -> Unit
) {
    GenericGrid(
        itemList = mediaContentList,
        maxCardsNumber = maxCardsNumber ?: mediaContentList.size
    ) { content, size ->
        DefaultContentCard(
            modifier = Modifier
                .width(size)
                .padding(
                    horizontal = BROWSE_CARD_PADDING_HORIZONTAL.dp,
                    vertical = BROWSE_CARD_PADDING_VERTICAL.dp
                ),
            cardWidth = size,
            imageUrl = content.poster_path,
            title = content.title,
            rating = content.vote_average,
            goToDetails = {
                openContentDetails(content.id, content.mediaType)
            }
        )
    }
}

@Composable
fun GridImageList(
    personImageList: List<PersonImage>,
    maxCardsNumber: Int? = null
) {
    GenericGrid(
        itemList = personImageList,
        maxCardsNumber = maxCardsNumber ?: personImageList.size
    ) { personImage, size ->
        PersonImages(
            modifier = Modifier.width(size),
            cardWidth = size,
            imageUrl = personImage.filePath
        )
    }
}
