package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.features.browse.ui.Browse
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.navigation.ScreenUI

class BrowseScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Browse(
            goToDetails = { contentId, mediaType ->
                navController.navigate(
                    DetailsScreen.routeWithArguments(contentId, mediaType.name)
                )
            }
        )
    }
}
