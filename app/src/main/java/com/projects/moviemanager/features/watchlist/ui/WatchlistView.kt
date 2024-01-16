package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Watchlist() {
    Watchlist(
        viewModel = hiltViewModel()
    )
}

@Composable
private fun Watchlist(
    viewModel: WatchlistViewModel
) {
    val watchlist by viewModel.watchlist.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Watchlist Screen",
            style = TextStyle(color = Color.Red)
        )
        watchlist.forEach {
            Text(text = it.contentId.toString())
            Text(text = it.mediaType)
        }
    }
}
