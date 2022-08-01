package gustavo.projects.moviemanager.domain.mappers

import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.network.response.GetMoviesByIdResponse

object MovieMapper {

    fun buildFrom(response: GetMoviesByIdResponse): Movie? {
        return if (response.vote_average != 0.0 && response.poster_path != null) {
            Movie(response.vote_average, response.id, response.poster_path, response.title)
        } else null
    }
}