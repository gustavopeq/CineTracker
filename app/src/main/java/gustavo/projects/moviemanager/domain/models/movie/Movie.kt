package gustavo.projects.moviemanager.domain.models.movie

import gustavo.projects.moviemanager.network.response.MovieApiResponse

data class Movie(
    val vote_average: Double?,
    val id: Int?,
    val poster_path: String?,
    val title: String?
)

fun MovieApiResponse.toDomain(): Movie? {
    return if (this.vote_average != 0.0 && this.poster_path != null) {
        Movie(this.vote_average, this.id, this.poster_path, this.title)
    } else {
        null
    }
}
