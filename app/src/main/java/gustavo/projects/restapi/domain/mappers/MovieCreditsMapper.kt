package gustavo.projects.restapi.domain.mappers

import gustavo.projects.restapi.domain.models.MovieCast
import gustavo.projects.restapi.domain.models.MovieCredits
import gustavo.projects.restapi.network.response.GetMovieCreditsByIdResponse

object MovieCreditsMapper {

    fun buildFrom(response: GetMovieCreditsByIdResponse): MovieCredits {

        val listOfCast = mutableListOf<MovieCast>()

        response.cast.forEach {
            listOfCast.add(MovieCast(it.character, it.name, it.profile_path))
        }

        return MovieCredits(response.id, listOfCast)
    }
}