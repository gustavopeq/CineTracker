package gustavo.projects.moviemanager.compose.common.ui.components

import androidx.annotation.StringRes
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.domain.models.util.MovieListType

sealed class SortTypeItem(
    @StringRes val sortTypeRes: Int,
    val type: MovieListType,
    val itemIndex: Int
) {
    data object NowPlaying : SortTypeItem(
        sortTypeRes = R.string.now_playing,
        type = MovieListType.NOW_PLAYING,
        itemIndex = 0
    )
    data object Popular : SortTypeItem(
        sortTypeRes = R.string.popular_movies,
        type = MovieListType.POPULAR,
        itemIndex = 1
    )
    data object TopRated : SortTypeItem(
        sortTypeRes = R.string.top_rated_movies,
        type = MovieListType.TOP_RATED,
        itemIndex = 2
    )
    data object Upcoming : SortTypeItem(
        sortTypeRes = R.string.upcoming_movies,
        type = MovieListType.UPCOMING,
        itemIndex = 3
    )
}
