package com.projects.moviemanager.features.watchlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.NetworkImage
import com.projects.moviemanager.common.ui.components.RatingComponent
import com.projects.moviemanager.common.ui.components.popup.GenericPopupMenu
import com.projects.moviemanager.common.ui.components.popup.PopupMenuItem
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.theme.PrimaryYellowColor_90
import com.projects.moviemanager.common.util.UiConstants.BROWSE_CARD_DEFAULT_ELEVATION
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.util.UiConstants.POSTER_ASPECT_RATIO_MULTIPLY
import com.projects.moviemanager.common.util.UiConstants.SMALL_PADDING
import com.projects.moviemanager.common.util.UiConstants.WATCHLIST_IMAGE_WIDTH
import com.projects.moviemanager.features.watchlist.model.DefaultLists.Companion.getOtherList
import com.projects.moviemanager.common.util.Constants.BASE_300_IMAGE_URL
import com.projects.moviemanager.common.util.UiConstants.MEDIA_TYPE_TAG_CORNER_SIZE
import com.projects.moviemanager.common.util.capitalized
import com.projects.moviemanager.features.watchlist.model.DefaultLists

@Composable
fun WatchlistCard(
    title: String,
    rating: Double,
    posterUrl: String?,
    mediaType: MediaType,
    selectedList: String,
    onCardClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onMoveItemToList: () -> Unit
) {
    val fullImageUrl = BASE_300_IMAGE_URL + posterUrl
    val imageWidth = WATCHLIST_IMAGE_WIDTH.dp
    val imageHeight = imageWidth * POSTER_ASPECT_RATIO_MULTIPLY


    var showPopupMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        colors = CardDefaults.cardColors(
            containerColor = MainBarGreyColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = BROWSE_CARD_DEFAULT_ELEVATION.dp
        )
    ) {
        Row(
            modifier = Modifier.height(imageHeight)
        ) {
            NetworkImage(
                imageUrl = fullImageUrl,
                widthDp = imageWidth,
                heightDp = imageHeight
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = DEFAULT_PADDING.dp)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
                RatingComponent(rating = rating)
                Spacer(modifier = Modifier.weight(1f))
                MediaTypeTag(
                    modifier = Modifier.clip(RoundedCornerShape(MEDIA_TYPE_TAG_CORNER_SIZE.dp)),
                    mediaType = mediaType
                )
            }
            IconButton(
                onClick = {
                    showPopupMenu = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more_options),
                    contentDescription = "More Options"
                )
                MoreOptionsPopUpMenu(
                    showMenu = showPopupMenu,
                    selectedList = selectedList,
                    onDismissRequest = {
                        showPopupMenu = false
                    },
                    onRemoveClick = onRemoveClick,
                    onMoveItemToList = onMoveItemToList
                )
            }
        }
    }
}

@Composable
fun MediaTypeTag(
    modifier: Modifier = Modifier,
    mediaType: MediaType
) {
    val mediaTypeTag = if (mediaType == MediaType.MOVIE) {
        stringResource(id = R.string.movie_tag)
    } else {
        stringResource(id = R.string.show_tag)
    }

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 50.dp)
            .background(color = PrimaryYellowColor_90),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = mediaTypeTag,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 2.dp)
        )
    }
}

@Composable
fun MoreOptionsPopUpMenu(
    showMenu: Boolean,
    selectedList: String,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    onMoveItemToList: () -> Unit
) {
    val primaryList = DefaultLists.getListById(selectedList)
    val listLocalizedName = stringResource(DefaultLists.getListLocalizedName(primaryList))

    val secondaryList = getOtherList(selectedList)
    val secondaryListLocalizedName = stringResource(
        DefaultLists.getListLocalizedName(secondaryList)
    )

    val menuItems = listOf(
        PopupMenuItem(
            title = stringResource(
                id = R.string.remove_option_popup_menu,
                listLocalizedName
            ),
            onClick = onRemoveClick
        ),
        PopupMenuItem(
            title = stringResource(
                id = R.string.move_to_list_option_popup_menu,
                secondaryListLocalizedName
            ),
            onClick = onMoveItemToList
        )
    )

    GenericPopupMenu(
        showMenu = showMenu,
        onDismissRequest = onDismissRequest,
        menuItems = menuItems
    )
}
