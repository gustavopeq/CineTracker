package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.ContentCard
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_MIN_CARD_WIDTH
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.MAX_COUNT_DETAILS_CARDS
import com.projects.moviemanager.common.ui.util.calculateCardsPerRow
import com.projects.moviemanager.common.ui.util.dpToPx
import com.projects.moviemanager.common.ui.util.pxToDp
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.util.removeParentPadding

@Composable
fun MoreLikeThisList(
    contentSimilarList: List<MediaContent>,
    openSimilarContent: (Int, MediaType) -> Unit
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
        val filteredList = contentSimilarList.take(MAX_COUNT_DETAILS_CARDS)
            .chunked(numCardsPerRow)

        filteredList.forEach { rowItems ->
            Row {
                rowItems.forEach { content ->
                    ContentCard(
                        modifier = Modifier.width(adjustedCardSize),
                        cardWidth = adjustedCardSize,
                        imageUrl = content.poster_path,
                        title = content.title,
                        rating = content.vote_average,
                        goToDetails = {
                            openSimilarContent(content.id, content.mediaType)
                        }
                    )
                }
            }
        }
    }
}
