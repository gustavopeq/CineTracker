package com.projects.moviemanager.compose.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.util.formatRating

@Composable
fun RatingComponent(
    modifier: Modifier = Modifier,
    rating: Double?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.offset(x = (-0.5).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null
        )
        Text(
            text = rating.formatRating(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
