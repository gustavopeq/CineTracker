package com.projects.moviemanager.features.watchlist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.popup.GenericPopupMenu
import com.projects.moviemanager.common.ui.components.popup.PopupMenuItem
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor

@Composable
fun ListRemovePopUpMenu(
    showRemoveMenu: Boolean,
    menuOffset: Offset,
    onRemoveList: () -> Unit,
    onDismiss: () -> Unit
) {
    val menuItems = listOf(
        PopupMenuItem(
            stringResource(id = R.string.delete_list_pop_up_item),
            onClick = onRemoveList
        )
    )
    Box(
        modifier = Modifier
            .absoluteOffset(x = (menuOffset.x / 2).dp)
    ) {
        GenericPopupMenu(
            showMenu = showRemoveMenu,
            backgroundColor = MainBarGreyColor,
            onDismissRequest = onDismiss,
            menuItems = menuItems
        )
    }
}