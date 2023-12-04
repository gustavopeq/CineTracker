package com.projects.moviemanager.compose.features.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.compose.common.MediaType
import timber.log.Timber

@Composable
fun Details(
    contentId: Int?,
    mediaType: MediaType
) {
    Details(
        viewModel = hiltViewModel(),
        contentId = contentId,
        mediaType = mediaType
    )
}

@Composable
private fun Details(
    viewModel: DetailsViewModel,
    contentId: Int?,
    mediaType: MediaType
) {
    LaunchedEffect(Unit) {
        if (contentId != null) {
            viewModel.printDetails(contentId)
        } else {
            Timber.tag("print").d("ContentId is null!")
        }
    }

   Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Details Screen",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(color = Color.Red)
        )
    }
}
