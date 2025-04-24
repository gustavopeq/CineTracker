package com.projects.moviemanager.common.ui.components

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.theme.PrimaryBlackColor
import com.projects.moviemanager.common.util.UiConstants.SYSTEM_BOTTOM_NAV_PADDING

@Composable
fun SystemNavBarSpacer() {
    Spacer(modifier = Modifier.height(SYSTEM_BOTTOM_NAV_PADDING.dp))
}

@Composable
fun SetStatusBarColor(newColor: Color = MainBarGreyColor) {
    if (Build.VERSION.SDK_INT < 35) {
        val context = LocalContext.current
        val window = (context as? Activity)?.window

        DisposableEffect(Unit) {
            window?.statusBarColor = newColor.toArgb()

            onDispose {
                window?.statusBarColor = PrimaryBlackColor.toArgb()
            }
        }
    }
}
