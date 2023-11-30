package gustavo.projects.moviemanager.domain.models.movie

data class MoviePage(
    val page: Int = 0,
    val results: List<Movie> = emptyList()
)
