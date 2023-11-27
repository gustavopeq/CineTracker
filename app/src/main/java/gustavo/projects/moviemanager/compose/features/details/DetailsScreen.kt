package gustavo.projects.moviemanager.compose.features.details

import gustavo.projects.moviemanager.compose.navigation.Screen

object DetailsScreen : Screen {
    private const val DETAILS_ROUTE = "details"
    override fun route(): String = DETAILS_ROUTE
}