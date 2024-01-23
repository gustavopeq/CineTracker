package com.projects.moviemanager.common.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.theme.PrimaryBlackColor

@Composable
fun SystemNavBarSpacer() {
    Spacer(
        modifier = Modifier.windowInsetsBottomHeight(
            WindowInsets.navigationBars
        )
    )
}

@Composable
fun SetStatusBarColor(
    newColor: Color = MainBarGreyColor
) {
    val context = LocalContext.current
    val window = (context as? Activity)?.window

    DisposableEffect(Unit) {
        window?.statusBarColor = newColor.toArgb()

        onDispose {
            window?.statusBarColor = PrimaryBlackColor.toArgb()
        }
    }
}
