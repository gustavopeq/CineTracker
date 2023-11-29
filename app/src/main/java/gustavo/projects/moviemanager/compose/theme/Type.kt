package gustavo.projects.moviemanager.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val AppTypography = Typography(
    // Display
    // Headline
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 18.sp,
        fontWeight = FontWeight(400),
        lineHeight = 28.sp
    ),
    // Title
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 22.sp,
        fontWeight = FontWeight(400),
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 18.sp,
        fontWeight = FontWeight(500),
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp,
        fontWeight = FontWeight(500),
        lineHeight = 24.sp
    ),
    // Body
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 24.sp,
        fontWeight = FontWeight(400),
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        lineHeight = 20.sp
    ),
    // Label
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 12.sp,
        fontWeight = FontWeight(400),
        lineHeight = 20.sp
    )
)
