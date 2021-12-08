package gustavo.projects.moviemanager.domain.mappers.person

import gustavo.projects.moviemanager.domain.models.MovieCast
import gustavo.projects.moviemanager.domain.models.person.PersonsMovieCast
import gustavo.projects.moviemanager.network.response.person.GetPersonsMoviesByIdResponse

object PersonsMovieCastMapper {

    fun buildFrom(response: GetPersonsMoviesByIdResponse): PersonsMovieCast {

        val listOfCast = mutableListOf<MovieCast>()

        response.cast?.forEach {
            listOfCast.add(MovieCast(it!!.character, it.id, it.title, it.poster_path))
        }

        return PersonsMovieCast(listOfCast, response.id!!)
    }
}