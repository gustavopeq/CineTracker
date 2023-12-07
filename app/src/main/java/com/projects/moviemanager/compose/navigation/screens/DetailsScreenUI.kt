package com.projects.moviemanager.compose.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.compose.features.details.DetailsScreen
import com.projects.moviemanager.compose.features.details.ui.Details
import com.projects.moviemanager.compose.navigation.ScreenUI

class DetailsScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        val contentId = navArguments?.getInt(DetailsScreen.ARG_ID)
        val mediaType = navArguments?.getString(DetailsScreen.ARG_MEDIA_TYPE)
        Details(
            contentId = contentId,
            mediaType = MediaType.getType(mediaType),
            onBackPress = {
                navController.popBackStack()
            }
        )
    }
}
