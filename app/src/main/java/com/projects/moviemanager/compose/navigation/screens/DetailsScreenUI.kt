package com.projects.moviemanager.compose.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.compose.features.details.ui.Details
import com.projects.moviemanager.compose.navigation.ScreenUI

class DetailsScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Details()
    }
}
