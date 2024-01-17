package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.classicVerticalGradientBrush
import com.projects.moviemanager.common.ui.util.UiConstants

@Composable
fun DetailsTopBar(
    showWatchlistButton: Boolean,
    isContentInWatchlist: Boolean,
    onBackBtnPress: () -> Unit,
    onWatchlistBtnPress: () -> Unit
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
    onClick: () -> Unit
) {
    val watchlistAddIcon = R.drawable.ic_baseline_bookmark_add
    val watchlistRemoveIcon = R.drawable.ic_baseline_bookmark_remove

    val iconId = if (isContentInWatchlist) watchlistRemoveIcon else watchlistAddIcon
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}
