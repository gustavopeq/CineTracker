package gustavo.projects.moviemanager.compose.features.browse

import gustavo.projects.moviemanager.compose.navigation.Screen

object BrowseScreen : Screen {
    private const val BROWSE_ROUTE = "browse"
    override fun route(): String = BROWSE_ROUTE
}