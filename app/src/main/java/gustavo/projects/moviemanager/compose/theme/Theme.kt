package gustavo.projects.carmanager.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import gustavo.projects.moviemanager.compose.theme.AppTypography
import gustavo.projects.moviemanager.compose.theme.BackgroundLight
import gustavo.projects.moviemanager.compose.theme.PrimaryLight
import gustavo.projects.moviemanager.compose.theme.SecondaryLight
import gustavo.projects.moviemanager.compose.theme.Shapes
import gustavo.projects.moviemanager.compose.theme.onPrimaryLight
import gustavo.projects.moviemanager.compose.theme.onPrimaryVariant
import gustavo.projects.moviemanager.compose.theme.unselectedGrey

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
        shapes = Shapes,
        content = content
    )
}
