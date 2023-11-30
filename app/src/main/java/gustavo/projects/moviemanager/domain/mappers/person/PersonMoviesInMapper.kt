package gustavo.projects.moviemanager.domain.mappers.person

import gustavo.projects.moviemanager.domain.models.movie.Movie
import gustavo.projects.moviemanager.domain.models.person.PersonMoviesIn
import gustavo.projects.moviemanager.network.response.person.GetPersonsMoviesByIdResponse

object PersonMoviesInMapper {

    fun buildFrom(response: GetPersonsMoviesByIdResponse): PersonMoviesIn {
        val listOfCast = mutableListOf<Movie>()

        response.cast?.forEach {
            listOfCast.add(Movie(it!!.vote_average, it!!.id, it!!.poster_path, it!!.title))
        }

        return PersonMoviesIn(listOfCast, response.id!!)
    }
}
