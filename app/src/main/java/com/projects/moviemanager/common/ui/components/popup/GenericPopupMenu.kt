package com.projects.moviemanager.common.ui.components.popup

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GenericPopupMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    menuItems: List<PopupMenuItem>
) {
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
                        style = MaterialTheme.typography.bodySmall
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

