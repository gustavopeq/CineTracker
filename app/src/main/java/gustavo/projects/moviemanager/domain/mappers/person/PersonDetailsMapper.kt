package gustavo.projects.moviemanager.domain.mappers.person

import gustavo.projects.moviemanager.domain.models.movie.Movie
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.domain.models.person.PersonImages
import gustavo.projects.moviemanager.network.response.person.GetPersonDetailsByIdResponse

object PersonDetailsMapper {

    fun buildFrom(
        response: GetPersonDetailsByIdResponse,
        movieList: List<Movie>,
        personImageList: PersonImages?
    ): PersonDetails {
        return PersonDetails(
            biography = response.biography,
            birthday = response.birthday,
            deathday = response.deathday,
            id = response.id,
            name = response.name,
            place_of_birth = response.place_of_birth,
            profile_path = response.profile_path,
            movieInList = movieList,
            personImageList = personImageList!!.imageList
        )
    }
}
