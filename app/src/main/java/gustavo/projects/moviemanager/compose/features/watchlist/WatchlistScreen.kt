package gustavo.projects.moviemanager.compose.features.watchlist

import gustavo.projects.moviemanager.compose.navigation.Screen

object WatchlistScreen : Screen {
    private const val WATCHLIST_ROUTE = "watchlist"
    override fun route(): String = WATCHLIST_ROUTE
}