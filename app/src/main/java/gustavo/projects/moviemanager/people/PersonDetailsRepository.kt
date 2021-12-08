package gustavo.projects.moviemanager.people

import gustavo.projects.moviemanager.domain.mappers.person.PersonDetailsMapper
import gustavo.projects.moviemanager.domain.mappers.person.PersonMoviesInMapper
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.network.NetworkLayer


class PersonDetailsRepository {

    suspend fun getPersonDetailsById(person_ID: Int) : PersonDetails? {

        val request = NetworkLayer.apiClient.getPersonDetailsById(person_ID)

        if(request.failed || !request.isSuccessful) {
            return null
        }

        val movieList = getPersonsMoviesById(person_ID)

        return PersonDetailsMapper.buildFrom(request.body, movieList)
    }

    private suspend fun getPersonsMoviesById(person_ID: Int) : List<Movie> {
        val request = NetworkLayer.apiClient.getPersonsMoviesById(person_ID)


        if(request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return PersonMoviesInMapper.buildFrom(request.body).cast
    }
}
