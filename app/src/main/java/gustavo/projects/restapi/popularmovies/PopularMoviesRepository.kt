package gustavo.projects.restapi.popularmovies

import gustavo.projects.restapi.domain.mappers.PopularMoviePageMapper
import gustavo.projects.restapi.domain.models.PopularMoviePage
import gustavo.projects.restapi.network.NetworkLayer

class PopularMoviesRepository {

    suspend fun getPopularMoviesPage(pageIndex: Int): PopularMoviePage?{
        val request = NetworkLayer.apiClient.getPopularMoviesPage(pageIndex)

        if(request.failed || !request.isSuccessful) {
            return null
        }

        return PopularMoviePageMapper.buildFrom(request.body)
    }
}