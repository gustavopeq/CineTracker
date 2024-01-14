package com.projects.moviemanager.common.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SystemNavBarSpacer() {
    Spacer(
        modifier = Modifier.windowInsetsBottomHeight(
            WindowInsets.navigationBars
        )
    )
}
