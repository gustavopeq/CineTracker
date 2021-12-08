package gustavo.projects.moviemanager.people

import gustavo.projects.moviemanager.domain.mappers.person.PersonDetailsMapper
import gustavo.projects.moviemanager.domain.mappers.person.PersonsMovieCastMapper
import gustavo.projects.moviemanager.domain.models.MovieCast
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.network.NetworkLayer


class PersonDetailsRepository {

    suspend fun getPersonDetailsById(person_ID: Int) : PersonDetails? {

        //Check if person is cached
        //val movie = MovieCache.movieMap[movie_ID]
        //if(movie != null) {
        //    return movie
        //}

        val request = NetworkLayer.apiClient.getPersonDetailsById(person_ID)

        if(request.failed || !request.isSuccessful) {
            return null
        }


        //Add movie to the cache list
        //MovieCache.movieMap[movie_ID] = movieDetail

        val movieCastList = getPersonsMoviesById(person_ID)

        return PersonDetailsMapper.buildFrom(request.body, movieCastList)
    }

    suspend fun getPersonsMoviesById(person_ID: Int) : List<MovieCast> {
        val request = NetworkLayer.apiClient.getPersonsMoviesById(person_ID)


        if(request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return PersonsMovieCastMapper.buildFrom(request.body).cast
    }
}
