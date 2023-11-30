package gustavo.projects.moviemanager.domain.models.person

import gustavo.projects.moviemanager.domain.models.movie.Movie

data class PersonMoviesIn(
    val cast: List<Movie>,
    val id: Int
)
