package gustavo.projects.moviemanager.domain.mappers

import gustavo.projects.moviemanager.domain.models.MovieCast
import gustavo.projects.moviemanager.domain.models.MovieDetails
import gustavo.projects.moviemanager.domain.models.MovieVideo
import gustavo.projects.moviemanager.network.response.GetMovieDetailsByIdResponse

object MovieDetailsMapper {

    fun buildFrom(
        response: GetMovieDetailsByIdResponse,
        cast: List<MovieCast>,
        movieVideo: List<MovieVideo?>?
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
                movieCast = cast,
                movieVideos = movieVideo
        )
    }
}