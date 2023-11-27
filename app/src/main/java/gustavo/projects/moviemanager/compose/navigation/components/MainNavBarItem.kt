package gustavo.projects.moviemanager.compose.navigation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.compose.features.browse.BrowseScreen
import gustavo.projects.moviemanager.compose.features.home.HomeScreen
import gustavo.projects.moviemanager.compose.features.search.SearchScreen
import gustavo.projects.moviemanager.compose.features.watchlist.WatchlistScreen
import gustavo.projects.moviemanager.compose.navigation.Screen

sealed class MainNavBarItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @DrawableRes val iconResId: Int
) {
    object Home : MainNavBarItem(
        screen = HomeScreen,
        labelResId = R.string.main_nav_home,
        iconResId = R.drawable.ic_home
    )
    object Browse : MainNavBarItem(
        screen = BrowseScreen,
        labelResId = R.string.main_nav_browse,
        iconResId = R.drawable.ic_browse
    )
    object Watchlist : MainNavBarItem(
        screen = WatchlistScreen,
        labelResId = R.string.main_nav_watchlist,
        iconResId = R.drawable.ic_watchlist
    )
    object Search : MainNavBarItem(
        screen = SearchScreen,
        labelResId = R.string.main_nav_search,
        iconResId = R.drawable.ic_search
    )
}
