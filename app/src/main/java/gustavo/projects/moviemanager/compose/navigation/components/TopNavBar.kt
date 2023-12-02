package gustavo.projects.moviemanager.compose.navigation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.compose.common.ui.util.UiConstants.BROWSE_SORT_ICON_SIZE
import gustavo.projects.moviemanager.compose.features.browse.BrowseScreen
import gustavo.projects.moviemanager.compose.features.home.HomeScreen
import gustavo.projects.moviemanager.compose.features.home.ui.components.HomeLogoAnimation
import gustavo.projects.moviemanager.compose.features.search.SearchScreen
import gustavo.projects.moviemanager.compose.features.watchlist.WatchlistScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    currentScreen: String?,
    displaySortScreen: (Boolean) -> Unit
) {
    val title = currentScreen.getScreenNameRes()?.let { stringResource(id = it) }

    TopAppBar(
        navigationIcon = {
            if (currentScreen == HomeScreen.route()) {
                HomeLogoAnimation()
            }
        },
        title = {
            Text(
                text = title.orEmpty(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            if (currentScreen == BrowseScreen.route()) {
                IconButton(
                    onClick = { displaySortScreen(true) }
                ) {
                    Icon(
                        modifier = Modifier.size(BROWSE_SORT_ICON_SIZE.dp),
                        painter = painterResource(id = R.drawable.ic_sort),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

private fun String?.getScreenNameRes(): Int? {
    return when (this) {
        HomeScreen.route() -> MainNavBarItem.Home.labelResId
        BrowseScreen.route() -> MainNavBarItem.Browse.labelResId
        WatchlistScreen.route() -> MainNavBarItem.Watchlist.labelResId
        SearchScreen.route() -> MainNavBarItem.Search.labelResId
        else -> null
    }
}
