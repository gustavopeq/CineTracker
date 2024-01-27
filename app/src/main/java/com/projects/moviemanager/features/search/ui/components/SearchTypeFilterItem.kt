package com.projects.moviemanager.features.search.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.MediaType

sealed class SearchTypeFilterItem(
    @StringRes val tabResId: Int,
    val mediaType: MediaType?
) {
    data object TopResults : SearchTypeFilterItem(
        tabResId = R.string.search_top_results_tab,
        mediaType = null
    )
    data object Movies : SearchTypeFilterItem(
        tabResId = R.string.search_movies_tab,
        mediaType = MediaType.MOVIE
    )
    data object Shows : SearchTypeFilterItem(
        tabResId = R.string.search_shows_tab,
        mediaType = MediaType.SHOW
    )
    data object Person : SearchTypeFilterItem(
        tabResId = R.string.search_person_tab,
        mediaType = MediaType.PERSON
    )
}
