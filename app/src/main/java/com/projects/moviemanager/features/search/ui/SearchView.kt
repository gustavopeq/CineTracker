package com.projects.moviemanager.features.search.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
    SearchBar(
        viewModel = viewModel
    )
}
