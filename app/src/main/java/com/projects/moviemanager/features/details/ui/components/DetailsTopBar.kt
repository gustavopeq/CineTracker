package com.projects.moviemanager.features.details.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.ui.components.popup.GenericPopupMenu
import com.projects.moviemanager.common.ui.components.popup.PopupMenuItem
import com.projects.moviemanager.common.util.UiConstants
import com.projects.moviemanager.common.util.UiConstants.RETURN_TOP_BAR_HEIGHT
import com.projects.moviemanager.common.util.dpToPx
import com.projects.moviemanager.features.details.util.mapValueToRange
import com.projects.moviemanager.features.watchlist.model.DefaultLists

@Composable
fun DetailsTopBar(
    contentTitle: String,
    currentHeaderPosY: Float,
    initialHeaderPosY: Float,
    showWatchlistButton: Boolean,
    contentInWatchlistStatus: Map<String, Boolean>,
    onBackBtnPress: () -> Unit,
    toggleWatchlist: (String) -> Unit
) {
    val barHeightFloat = dpToPx(RETURN_TOP_BAR_HEIGHT.dp, density = LocalDensity.current)
    val alpha = currentHeaderPosY.mapValueToRange(
        initialHeaderPosY - barHeightFloat * 2
    )

    val gradientColor = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primary.copy(
            alpha = (1f - (alpha * 2)).coerceIn(minimumValue = 0f, maximumValue = 1f)
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(RETURN_TOP_BAR_HEIGHT.dp)
            .classicVerticalGradientBrush(
                colorList = gradientColor
            )
            .zIndex(UiConstants.FOREGROUND_INDEX),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackBtnPress() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = stringResource(id = R.string.back_arrow_description)
            )
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            this@Row.AnimatedVisibility(
                visible = currentHeaderPosY < 0,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = contentTitle,
                    style = MaterialTheme.typography.displaySmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }

        if (showWatchlistButton) {
            WatchlistButtonIcon(
                contentInWatchlistStatus = contentInWatchlistStatus,
                toggleWatchlist = toggleWatchlist
            )
        }
    }
}

@Composable
private fun WatchlistButtonIcon(
    contentInWatchlistStatus: Map<String, Boolean>,
    toggleWatchlist: (String) -> Unit
) {
    var showPopupMenu by remember { mutableStateOf(false) }
    val color = if (contentInWatchlistStatus.values.contains(true)) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    IconButton(
        onClick = {
            showPopupMenu = true
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_watchlist),
            contentDescription = null,
            tint = color
        )
        WatchlistPopUpMenu(
            showMenu = showPopupMenu,
            contentInWatchlistStatus = contentInWatchlistStatus,
            onDismissRequest = {
                showPopupMenu = false
            },
            toggleWatchlist = toggleWatchlist
        )
    }
}

@Composable
fun WatchlistPopUpMenu(
    showMenu: Boolean,
    contentInWatchlistStatus: Map<String, Boolean>,
    onDismissRequest: () -> Unit,
    toggleWatchlist: (String) -> Unit
) {
    val watchlist = stringResource(id = R.string.watchlist_tab)
    val watchlistMenuTitle = if (contentInWatchlistStatus[DefaultLists.WATCHLIST.listId] == true) {
        stringResource(
            id = R.string.remove_option_popup_menu,
            watchlist
        )
    } else {
        stringResource(
            id = R.string.add_option_popup_menu,
            watchlist
        )
    }

    val watched = stringResource(id = R.string.watched_tab)
    val watchedMenuTitle = if (contentInWatchlistStatus[DefaultLists.WATCHED.listId] == true) {
        stringResource(
            id = R.string.remove_option_popup_menu,
            watched
        )
    } else {
        stringResource(
            id = R.string.add_option_popup_menu,
            watched
        )
    }

    val menuItems = listOf(
        PopupMenuItem(
            title = watchlistMenuTitle,
            onClick = {
                toggleWatchlist(DefaultLists.WATCHLIST.listId)
            }
        ),
        PopupMenuItem(
            title = watchedMenuTitle,
            onClick = {
                toggleWatchlist(DefaultLists.WATCHED.listId)
            }
        )
    )

    GenericPopupMenu(showMenu, onDismissRequest, menuItems)
}
