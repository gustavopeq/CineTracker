package gustavo.projects.restapi.domain.mappers

import gustavo.projects.restapi.domain.models.Movie
import gustavo.projects.restapi.network.response.GetMovieByIdResponse

object MovieMapper {

    fun buildFrom(response: GetMovieByIdResponse) : Movie {

        return Movie(
                budget = response.budget,
                genres = response.genres,
                id = response.id,
                overview = response.overview,
                poster_path = response.poster_path,
                release_date = response.release_date,
                revenue = response.revenue,
                runtime = response.runtime,
                title = response.title,
                vote_average = response.vote_average
        )
    }
}