package com.projects.moviemanager.features.watchlist.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.projects.moviemanager.R

@Composable
fun MoreOptionsPopUpMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveClick: () -> Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.remove_options_popup_menu),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            onClick = {
                onRemoveClick()
                onDismissRequest()
            }
        )
    }
}
