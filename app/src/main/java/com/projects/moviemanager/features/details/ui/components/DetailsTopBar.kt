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
import com.projects.moviemanager.common.ui.components.popup.PopupMenuItem
import com.projects.moviemanager.common.ui.util.UiConstants
import com.projects.moviemanager.features.watchlist.model.DefaultLists

@Composable
fun DetailsTopBar(
    showWatchlistButton: Boolean,
    contentInWatchlistStatus: Map<String, Boolean>,
    onBackBtnPress: () -> Unit,
    toggleWatchlist: (String) -> Unit
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
    val watchlistMenuTitle = if (contentInWatchlistStatus[DefaultLists.WATCHLIST.listId] == true) {
        "Remove from Watchlist"
    } else {
        "Add to Watchlist"
    }
    val watchedMenuTitle = if (contentInWatchlistStatus[DefaultLists.WATCHED.listId] == true) {
        "Remove from Watched"
    } else {
        "Add to Watched"
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

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
    ) {
        menuItems.forEach { menuItem ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = menuItem.title,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                onClick = {
                    menuItem.onClick()
                    onDismissRequest()
                }
            )
        }
    }
}
