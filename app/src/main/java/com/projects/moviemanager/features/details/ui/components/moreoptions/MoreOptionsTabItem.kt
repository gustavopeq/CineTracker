package com.projects.moviemanager.features.details.ui.components.moreoptions

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.tab.TabItem

sealed class MoreOptionsTabItem(
    @StringRes override val tabResId: Int,
    override var tabIndex: Int = -1
) : TabItem {
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
    data object ImagesTab : MoreOptionsTabItem(
        tabResId = R.string.images_tab
    )
}
