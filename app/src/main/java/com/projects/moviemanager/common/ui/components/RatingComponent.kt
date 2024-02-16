package com.projects.moviemanager.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.util.UiConstants.RATING_STAR_DEFAULT_SIZE
import com.projects.moviemanager.common.util.formatRating

@Composable
fun RatingComponent(
    modifier: Modifier = Modifier,
    rating: Double?,
    ratingIconSize: Int? = RATING_STAR_DEFAULT_SIZE,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.offset(x = (-0.5).dp)
    ) {
        Image(
            modifier = Modifier.size((ratingIconSize ?: RATING_STAR_DEFAULT_SIZE).dp),
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null
        )
        Text(
            text = rating.formatRating(LocalContext.current),
            color = MaterialTheme.colorScheme.onPrimary,
            style = textStyle
        )
    }
}
