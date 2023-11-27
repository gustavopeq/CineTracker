package gustavo.projects.moviemanager.compose.navigation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import gustavo.projects.moviemanager.compose.navigation.ScreenUI
import gustavo.projects.moviemanager.compose.features.watchlist.ui.Watchlist

class WatchlistScreenUI : ScreenUI {
    @Composable
    override fun UI(navController: NavController, navArguments: Bundle?) {
        Watchlist()
    }
}
