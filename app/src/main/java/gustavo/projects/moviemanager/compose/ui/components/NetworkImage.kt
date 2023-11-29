package gustavo.projects.moviemanager.compose.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    widthDp: Dp,
    heightDp: Dp
) {
    val loadingSuccess = remember { mutableStateOf(false) }
    Box {
        GlideImage(
            modifier = modifier.size(widthDp, heightDp),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        ) {
            it.listener(
                object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingSuccess.value = true
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }
            )
        }
        if (!loadingSuccess.value) {
            ComponentPlaceholder(
                widthDp = widthDp,
                heightDp = heightDp,
                modifier = modifier
            )
        }
    }
}
