package gustavo.projects.moviemanager.compose.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import gustavo.projects.moviemanager.compose.features.browse.ui.Browse
import gustavo.projects.moviemanager.compose.navigation.ScreenUI

class BrowseScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Browse()
    }
}