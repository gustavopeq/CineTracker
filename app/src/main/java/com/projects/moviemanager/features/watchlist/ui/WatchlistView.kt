package com.projects.moviemanager.features.watchlist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Watchlist Screen",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(color = Color.Red)
        )
    }
}
