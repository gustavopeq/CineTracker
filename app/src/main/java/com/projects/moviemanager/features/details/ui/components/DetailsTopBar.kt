package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.features.watchlist.ui.components.MoreOptionsPopUpMenu

@Composable
fun DetailsTopBar(
    showWatchlistButton: Boolean,
    isContentInWatchlist: Boolean,
    onBackBtnPress: () -> Unit,
    onWatchlistBtnPress: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(UiConstants.RETURN_TOP_BAR_HEIGHT.dp)
            .classicVerticalGradientBrush()
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
        if (showWatchlistButton) {
            Spacer(modifier = Modifier.weight(1f))
            WatchlistButtonIcon(
                isContentInWatchlist = isContentInWatchlist,
                onClick = onWatchlistBtnPress
            )
        }
    }
}

@Composable
private fun WatchlistButtonIcon(
    isContentInWatchlist: Boolean,
    onClick: (String) -> Unit
) {
    val watchlistAddIcon = R.drawable.ic_baseline_bookmark_add
    val watchlistRemoveIcon = R.drawable.ic_baseline_bookmark_remove
    val iconId = if (isContentInWatchlist) watchlistRemoveIcon else watchlistAddIcon

    var showPopupMenu by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            showPopupMenu = true
        }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        WatchlistPopUpMenu(
            showMenu = showPopupMenu,
            onDismissRequest = {
                showPopupMenu = false
            },
            onAddToWatchlist = onClick,
            onAddToWatched = onClick
        )
    }
}

@Composable
fun WatchlistPopUpMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    onAddToWatchlist: (String) -> Unit,
    onAddToWatched: (String) -> Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "Add to Watchlist",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            onClick = {
                onAddToWatchlist(DefaultLists.WATCHLIST.listId)
                onDismissRequest()
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = "Add to Watched",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            onClick = {
                onAddToWatched(DefaultLists.WATCHED.listId)
                onDismissRequest()
            }
        )
    }
}
