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

/**
 * Function to convert header position into a value between 0.0 and 1.0.
 * Used to define the background image alpha based on the header position.
 */
fun Float.mapValueToRange(initialHeaderPosY: Float): Float {
    val maxValue = 0f
    val mappedValue = (this - initialHeaderPosY) / (maxValue - initialHeaderPosY)
    return 1.0f - mappedValue
}
