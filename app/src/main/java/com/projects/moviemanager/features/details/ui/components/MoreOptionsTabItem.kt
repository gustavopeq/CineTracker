package com.projects.moviemanager.features.details.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R

sealed class MoreOptionsTabItem(
    @StringRes val tabResId: Int,
    var tabIndex: Int = -1
) {
    data object VideosTab : MoreOptionsTabItem(
        tabResId = R.string.more_options_videos
    )
    data object MoreLikeThisTab : MoreOptionsTabItem(
        tabResId = R.string.more_options_similar
    )
    data object MoviesTab : MoreOptionsTabItem(
        tabResId = R.string.movies_tab
    )
    data object ShowsTab : MoreOptionsTabItem(
        tabResId = R.string.shows_tab
    )
}
