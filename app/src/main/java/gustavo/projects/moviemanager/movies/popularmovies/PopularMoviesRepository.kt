package gustavo.projects.moviemanager.movies.popularmovies

import gustavo.projects.moviemanager.domain.mappers.MoviePageMapper
import gustavo.projects.moviemanager.domain.models.MoviePage
import gustavo.projects.moviemanager.network.NetworkLayer

class PopularMoviesRepository {

    suspend fun getPopularMoviesPage(pageIndex: Int): MoviePage?{
        val request = NetworkLayer.apiClient.getPopularMoviesPage(pageIndex)

        if(request.failed || !request.isSuccessful) {
            return null
        }

        return MoviePageMapper.buildFrom(request.body)
    }
}