package com.projects.moviemanager.compose.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight
)

private val LightColorPalette = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = onPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = Color.White,
    tertiary = unselectedGrey,
    surface = PrimaryLight,
    onSurface = onPrimaryLight,
    onSurfaceVariant = onPrimaryVariant,
    background = BackgroundLight
)

@Composable
fun MovieManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = AppTypography,
        shapes = RoundCornerShapes,
        content = content
    )
}
