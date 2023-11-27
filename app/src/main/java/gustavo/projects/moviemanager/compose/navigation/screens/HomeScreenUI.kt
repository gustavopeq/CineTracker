package gustavo.projects.moviemanager.compose.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import gustavo.projects.moviemanager.compose.features.home.ui.Home
import gustavo.projects.moviemanager.compose.navigation.ScreenUI

class HomeScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Home()
    }
}
