package gustavo.projects.moviemanager.domain.models.person

import gustavo.projects.moviemanager.domain.models.MovieCast


data class PersonsMovieCast(
        val cast: List<MovieCast>,
        val id: Int
)