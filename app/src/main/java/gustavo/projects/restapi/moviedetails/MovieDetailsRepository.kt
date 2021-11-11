package gustavo.projects.restapi.moviedetails


import gustavo.projects.restapi.domain.mappers.MovieCreditsMapper
import gustavo.projects.restapi.domain.mappers.MovieDetailsMapper
import gustavo.projects.restapi.domain.models.MovieDetails
import gustavo.projects.restapi.domain.models.MovieCast
import gustavo.projects.restapi.network.MovieCache
import gustavo.projects.restapi.network.NetworkLayer


class MovieDetailsRepository {

    suspend fun getMovieById(movie_ID: Int) : MovieDetails? {

        //Check if movie is cached
        val movie = MovieCache.movieMap[movie_ID]
        if(movie != null) {
            return movie
        }

        val request = NetworkLayer.apiClient.getMovieById(movie_ID)

        if(request.failed || !request.isSuccessful) {
            return null
        }

        val requestMovieCast = getMovieCastById(movie_ID)
        val movieDetail = MovieDetailsMapper.buildFrom(request.body, requestMovieCast)

        //Add movie to the cache list
        MovieCache.movieMap[movie_ID] = movieDetail

        return movieDetail
    }

    private suspend fun getMovieCastById(movie_ID: Int) : List<MovieCast> {

        val request = NetworkLayer.apiClient.getMovieCreditsById(movie_ID)

        if(request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return MovieCreditsMapper.buildFrom(request.body).cast
    }
}
