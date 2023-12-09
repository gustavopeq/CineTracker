package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.ContentCard
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.domain.models.content.MediaContent

@Composable
fun MoreLikeThisList(
    contentSimilarList: List<MediaContent>,
    openSimilarContent: (Int, MediaType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val filteredList = contentSimilarList.take(UiConstants.MAX_COUNT_DETAILS_CARDS)
            .chunked(UiConstants.DETAILS_CARDS_PER_ROW)

        filteredList.forEach { rowItems ->
            Row {
                rowItems.forEach { content ->
                    ContentCard(
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