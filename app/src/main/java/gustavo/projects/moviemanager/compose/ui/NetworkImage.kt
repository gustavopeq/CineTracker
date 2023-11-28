package gustavo.projects.moviemanager.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    GlideImage(
        modifier = modifier,
        model = imageUrl,
        contentDescription = null
    )
}
