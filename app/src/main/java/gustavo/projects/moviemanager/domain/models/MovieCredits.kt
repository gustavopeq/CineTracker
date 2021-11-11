package gustavo.projects.moviemanager.domain.models

data class MovieCredits(
        val id: Int = 0,
        val cast: List<MovieCast> = emptyList()
)
