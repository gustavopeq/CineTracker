package gustavo.projects.moviemanager.domain.mappers.person

import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.network.response.person.GetPersonDetailsById

object PersonDetailsMapper {

    fun buildFrom(response: GetPersonDetailsById): PersonDetails {
        return PersonDetails(
                biography = response.biography,
                birthday = response.birthday,
                deathday = response.deathday,
                id = response.id,
                name = response.name,
                place_of_birth = response.place_of_birth,
                profile_path = response.profile_path
        )
    }
}