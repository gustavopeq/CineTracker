package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.projects.moviemanager.common.ui.screen.ErrorScreen
import com.projects.moviemanager.features.details.DetailsScreen
import com.projects.moviemanager.features.details.ui.Details
import com.projects.moviemanager.navigation.ScreenUI

class DetailsScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = currentBackStackEntry?.destination?.route

        val backStackEntry = remember {
            navController.getBackStackEntry(DetailsScreen.route())
        }

        Details(
            navBackStackEntry = backStackEntry,
            onBackPress = {
                navController.popBackStack()
            },
            openSimilarContent = { id, type ->
                navController.navigate(
                    DetailsScreen.routeWithArguments(id, type.name)
                )
            },
            goToErrorScreen = {
                if (currentScreen != ErrorScreen.route()) {
                    navController.navigate(ErrorScreen.route())
                }
            }
        )
    }
}
