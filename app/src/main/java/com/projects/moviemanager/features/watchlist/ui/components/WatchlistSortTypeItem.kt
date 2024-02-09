package com.projects.moviemanager.features.watchlist.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.util.MediaType

sealed class WatchlistSortTypeItem(
    @StringRes val titleRes: Int,
    val mediaType: MediaType
) {
    data object MovieOnly : WatchlistSortTypeItem(
        titleRes = R.string.movie_tag,
        mediaType = MediaType.MOVIE
    )
    data object ShowOnly : WatchlistSortTypeItem(
        titleRes = R.string.show_tag,
        mediaType = MediaType.SHOW
    )
}
