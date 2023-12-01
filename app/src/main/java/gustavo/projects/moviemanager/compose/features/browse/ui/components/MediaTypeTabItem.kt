package gustavo.projects.moviemanager.compose.features.browse.ui.components

import androidx.annotation.StringRes
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.compose.common.MediaType

sealed class MediaTypeTabItem(
    @StringRes val tabResId: Int,
    val mediaType: MediaType
) {
    data object Movies : MediaTypeTabItem(
        tabResId = R.string.movies_tab,
        mediaType = MediaType.MOVIE
    )
    data object Shows : MediaTypeTabItem(
        tabResId = R.string.shows_tab,
        mediaType = MediaType.SHOW
    )
}
