package gustavo.projects.moviemanager.compose.navigation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController

@Composable
fun MainNavBar(
    navController: NavController,
    navBarItems: List<MainNavBarItem>
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        navBarItems.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate(item.screen.route()) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = stringResource(id = item.labelResId)
                    )
                }
            )
        }
    }
}
