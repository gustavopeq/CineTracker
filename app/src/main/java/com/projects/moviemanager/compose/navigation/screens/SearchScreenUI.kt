package com.projects.moviemanager.compose.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.compose.navigation.ScreenUI
import com.projects.moviemanager.compose.features.search.ui.Search

class SearchScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Search()
    }
}
