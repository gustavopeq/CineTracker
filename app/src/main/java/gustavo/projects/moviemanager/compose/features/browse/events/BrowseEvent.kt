package gustavo.projects.moviemanager.compose.features.browse.events

import gustavo.projects.moviemanager.domain.models.util.MovieListType

sealed class BrowseEvent {
    data class UpdateSortType(
        val movieListType: MovieListType
    ) : BrowseEvent()
}
