package gustavo.projects.restapi

import gustavo.projects.restapi.domain.mappers.MovieMapper
import gustavo.projects.restapi.domain.models.Movie
import gustavo.projects.restapi.network.NetworkLayer


class SharedRepository {

    suspend fun getMovieById(movie_ID: Int) : Movie? {

        val request = NetworkLayer.apiClient.getMovieById(movie_ID)

        if(request.failed) {
            return null
        }

        if(!request.isSuccessful){
            return null
        }

        return MovieMapper.buildFrom(request.body)
    }
}
