package gustavo.projects.restapi.popularmovies

import gustavo.projects.restapi.network.NetworkLayer
import gustavo.projects.restapi.network.response.GetPopularMoviesPageResponse

class PopularMoviesRepository {

    suspend fun getPopularMoviesPage(pageIndex: Int): GetPopularMoviesPageResponse?{
        val request = NetworkLayer.apiClient.getPopularMoviesPage(pageIndex)

        if(request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}