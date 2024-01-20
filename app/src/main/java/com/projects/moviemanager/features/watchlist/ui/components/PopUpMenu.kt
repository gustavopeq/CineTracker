package com.projects.moviemanager.features.watchlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MoreOptionsPopUpMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit,
    onMoveClick: () -> Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
    ) {
        DropdownMenuItem(
            text = {
                Text("Remove from Watchlist")
            },
            onClick = {
                onRemoveClick()
                onDismissRequest()
            }
        )
        DropdownMenuItem(
            text = {
                Text("Move to other list")
            },
            onClick = {
                onMoveClick()
                onDismissRequest()
            }
        )
    }
}