package com.projects.moviemanager.features.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.projects.moviemanager.common.ui.components.SetStatusBarColor
import com.projects.moviemanager.features.search.ui.components.SearchBar

@Composable
fun Search() {
    Search(
        viewModel = hiltViewModel()
    )
}

@Composable
private fun Search(
    viewModel: SearchViewModel
) {
    SetStatusBarColor()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            viewModel = viewModel
        )
    }
}
