package com.projects.moviemanager.common.ui.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.util.UiConstants.BROWSE_SORT_ICON_SIZE

@Composable
fun SortIconButton(displaySortScreen: (Boolean) -> Unit) {
    IconButton(
        onClick = { displaySortScreen(true) }
    ) {
        Icon(
            modifier = Modifier.size(BROWSE_SORT_ICON_SIZE.dp),
            painter = painterResource(id = R.drawable.ic_sort),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null
        )
    }
}
