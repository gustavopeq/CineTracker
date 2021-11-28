package gustavo.projects.moviemanager.movies.details


import gustavo.projects.moviemanager.domain.mappers.MovieCreditsMapper
import gustavo.projects.moviemanager.domain.mappers.MovieDetailsMapper
import gustavo.projects.moviemanager.domain.models.MovieDetails
import gustavo.projects.moviemanager.domain.models.MovieCast
import gustavo.projects.moviemanager.domain.models.MovieVideo
import gustavo.projects.moviemanager.network.MovieCache
import gustavo.projects.moviemanager.network.NetworkLayer


class MovieDetailsRepository {

    suspend fun getMovieDetailsById(movie_ID: Int) : MovieDetails? {

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
        val requestMovieVideo = getMovieVideosById(movie_ID)
        val movieDetail = MovieDetailsMapper.buildFrom(request.body, requestMovieCast, requestMovieVideo)

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

    private suspend fun getMovieVideosById(movie_ID: Int) : List<MovieVideo?>? {

        val request = NetworkLayer.apiClient.getMovieVideosById(movie_ID)

        if(request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body.results
    }
}
