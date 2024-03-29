package com.projects.moviemanager.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.projects.moviemanager.common.ui.screen.ErrorScreen
import com.projects.moviemanager.navigation.ScreenUI

class ErrorScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        ErrorScreen(
            onTryAgain = {
                navController.popBackStack()
            }
        )
    }
}
