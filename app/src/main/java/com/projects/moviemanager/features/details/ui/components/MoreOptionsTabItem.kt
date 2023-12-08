package com.projects.moviemanager.features.details.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R

sealed class MoreOptionsTabItem(
    @StringRes val tabResId: Int,
    val tabIndex: Int
) {
    data object Videos : MoreOptionsTabItem(
        tabResId = R.string.more_options_videos,
        tabIndex = 0
    )
    data object MoreLikeThis : MoreOptionsTabItem(
        tabResId = R.string.more_options_similar,
        tabIndex = 1
    )
}
