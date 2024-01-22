package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.features.watchlist.ui.Watchlist
import com.projects.moviemanager.navigation.ScreenUI

class WatchlistScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Watchlist(
            goToDetails = { contentId, mediaType ->
                navController.navigate(
                    DetailsScreen.routeWithArguments(contentId, mediaType.name)
                )
            }
        )
    }
}
