package com.projects.moviemanager.common.ui.components

import androidx.annotation.StringRes
import com.projects.moviemanager.R
import com.projects.moviemanager.domain.models.util.ContentListType

sealed class SortTypeItem(
    @StringRes val titleRes: Int,
    val listType: ContentListType,
    val itemIndex: Int
) {
    // Movies sorting
    data object NowPlaying : SortTypeItem(
        titleRes = R.string.now_playing,
        listType = ContentListType.MOVIE_NOW_PLAYING,
        itemIndex = 0
    )
    data object Popular : SortTypeItem(
        titleRes = R.string.popular_movies,
        listType = ContentListType.MOVIE_POPULAR,
        itemIndex = 1
    )
    data object TopRated : SortTypeItem(
        titleRes = R.string.top_rated_movies,
        listType = ContentListType.MOVIE_TOP_RATED,
        itemIndex = 2
    )
    data object Upcoming : SortTypeItem(
        titleRes = R.string.upcoming_movies,
        listType = ContentListType.MOVIE_UPCOMING,
        itemIndex = 3
    )

    // Shows sorting
    data object AiringToday : SortTypeItem(
        titleRes = R.string.show_airing_today,
        listType = ContentListType.SHOW_AIRING_TODAY,
        itemIndex = 0
    )
    data object ShowPopular : SortTypeItem(
        titleRes = R.string.show_popular,
        listType = ContentListType.SHOW_POPULAR,
        itemIndex = 1
    )
    data object ShowTopRated : SortTypeItem(
        titleRes = R.string.show_top_rated,
        listType = ContentListType.SHOW_TOP_RATED,
        itemIndex = 2
    )
    data object OnTheAir : SortTypeItem(
        titleRes = R.string.show_on_the_air,
        listType = ContentListType.SHOW_ON_THE_AIR,
        itemIndex = 3
    )
}
