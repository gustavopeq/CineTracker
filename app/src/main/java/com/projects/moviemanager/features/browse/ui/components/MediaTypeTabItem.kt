package com.projects.moviemanager.features.browse.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType

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
