package gustavo.projects.moviemanager.compose.navigation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gustavo.projects.moviemanager.compose.features.home.HomeScreen
import gustavo.projects.moviemanager.compose.features.home.ui.components.HomeLogoAnimation
import gustavo.projects.moviemanager.compose.util.UiConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    currentScreen: String?,
    screenTitle: String
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentScreen == HomeScreen.route()) {
                    HomeLogoAnimation()
                    Spacer(modifier = Modifier.width(UiConstants.DEFAULT_PADDING.dp))
                }
                Text(
                    text = screenTitle,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    )
}
