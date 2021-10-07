package gustavo.projects.restapi

import gustavo.projects.restapi.network.response.GetMovieByIdResponse
import gustavo.projects.restapi.network.NetworkLayer


class SharedRepository {

    suspend fun getMovieById(movie_ID: Int) : GetMovieByIdResponse? {

        val request = NetworkLayer.apiClient.getMovieById(movie_ID)

        if(request.failed) {
            return null
        }

        if(!request.isSuccessful){
            return null
        }

        return request.body
    }
}
