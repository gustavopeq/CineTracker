package com.projects.moviemanager.compose.features.details.util

import androidx.compose.runtime.Composable

@Composable
fun Int.stringFormat(): String {
    val runtimeHours: Int = this / 60
    val runtimeMinutes = this % 60

    return if (runtimeMinutes != 0) {
        "$runtimeHours h $runtimeMinutes min"
    } else {
        "$runtimeHours h"
    }
}
