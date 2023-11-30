package gustavo.projects.moviemanager.network.models.movie

import gustavo.projects.moviemanager.network.response.MovieApiResponse

data class MovieList(
    val page: Int = 0,
    val results: List<MovieApiResponse> = emptyList()
)
