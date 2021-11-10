package gustavo.projects.restapi.domain.mappers

import gustavo.projects.restapi.domain.models.MovieCast
import gustavo.projects.restapi.domain.models.MovieDetails
import gustavo.projects.restapi.network.response.GetMovieByIdResponse
import gustavo.projects.restapi.network.response.GetMovieCastByIdResponse
import gustavo.projects.restapi.network.response.GetMovieCreditsByIdResponse

object MovieDetailsMapper {

    fun buildFrom(
            response: GetMovieByIdResponse,
            cast: List<MovieCast>
    ) : MovieDetails {

        return MovieDetails(
                budget = response.budget,
                genres = response.genres,
                id = response.id,
                overview = response.overview,
                poster_path = response.poster_path,
                release_date = response.release_date,
                revenue = response.revenue,
                runtime = response.runtime,
                title = response.title,
                vote_average = response.vote_average,
                movieCast = cast
        )
    }
}