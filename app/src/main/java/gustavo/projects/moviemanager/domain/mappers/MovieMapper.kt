package gustavo.projects.moviemanager.domain.mappers

import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.network.response.GetMoviesByIdResponse

object MovieMapper {

    fun buildFrom(response: GetMoviesByIdResponse): Movie {
        return Movie(response.vote_average, response.id, response.poster_path, response.title)
    }
}