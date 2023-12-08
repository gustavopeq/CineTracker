package com.projects.moviemanager.features.details.util

import androidx.compose.runtime.Composable

@Composable
fun Int.stringFormat(): String {
    val runtimeHours: Int = this / 60
    val runtimeMinutes = this % 60

    return if (runtimeHours != 0 && runtimeMinutes != 0) {
        "$runtimeHours h $runtimeMinutes min"
    } else if (runtimeHours != 0) {
        "$runtimeHours h"
    } else {
        "$runtimeMinutes min"
    }
}
