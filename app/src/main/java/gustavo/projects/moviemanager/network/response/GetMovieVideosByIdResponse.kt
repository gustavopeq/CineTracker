package gustavo.projects.moviemanager.network.response

import gustavo.projects.moviemanager.domain.models.MovieVideo

data class GetMovieVideosByIdResponse(
    val id: Int?,
    val results: List<MovieVideo?>?
)