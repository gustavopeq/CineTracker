package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.features.details.ui.Details
import com.projects.moviemanager.navigation.ScreenUI

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
            },
            openSimilarContent = { id, type ->
                navController.navigate(
                    DetailsScreen.routeWithArguments(id, type.name)
                )
            }
        )
    }
}
