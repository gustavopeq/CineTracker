package gustavo.projects.restapi

import gustavo.projects.restapi.network.NetworkLayer
import gustavo.projects.restapi.network.response.GetPopularMoviesPageResponse

class MoviesRepository {

    suspend fun getPopularMoviesList(pageIndex: Int): GetPopularMoviesPageResponse{
        val request = NetworkLayer.apiClient.getPopularMoviesPage(pageIndex)

        if(request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }
}