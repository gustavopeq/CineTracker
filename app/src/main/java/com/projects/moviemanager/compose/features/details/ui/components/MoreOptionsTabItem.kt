package com.projects.moviemanager.compose.features.details.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R

sealed class MoreOptionsTabItem(
    @StringRes val tabResId: Int
) {
    data object Videos : MoreOptionsTabItem(
        tabResId = R.string.more_options_videos
    )
    data object MoreLikeThis : MoreOptionsTabItem(
        tabResId = R.string.more_options_similar
    )
}
