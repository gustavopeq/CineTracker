package gustavo.projects.moviemanager.domain.mappers.person

import gustavo.projects.moviemanager.domain.models.MovieCast
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.network.response.person.GetPersonDetailsByIdResponse

object PersonDetailsMapper {

    fun buildFrom(
        response: GetPersonDetailsByIdResponse,
        cast: List<MovieCast>
    ): PersonDetails {
        return PersonDetails(
                biography = response.biography,
                birthday = response.birthday,
                deathday = response.deathday,
                id = response.id,
                name = response.name,
                place_of_birth = response.place_of_birth,
                profile_path = response.profile_path,
                movieCast = cast
        )
    }
}