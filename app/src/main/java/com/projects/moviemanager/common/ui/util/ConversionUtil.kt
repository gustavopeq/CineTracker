package com.projects.moviemanager.common.ui.util

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun pxToDp(pixels: Int, density: Density): Dp {
    return with(density) { pixels.toDp() }
}

fun dpToPx(dp: Dp, density: Density): Int {
    return with(density) { dp.roundToPx() }
}
