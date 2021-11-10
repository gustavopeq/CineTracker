package gustavo.projects.restapi.domain.mappers

import gustavo.projects.restapi.domain.models.PopularMovie
import gustavo.projects.restapi.network.response.GetPopularMoviesByIdResponse

object PopularMovieMapper {

    fun buildFrom(response: GetPopularMoviesByIdResponse): PopularMovie {
        return PopularMovie(response.vote_average, response.id, response.poster_path, response.title)
    }
}