package gustavo.projects.moviemanager.network

import gustavo.projects.moviemanager.network.models.movie.MovieList
import gustavo.projects.moviemanager.network.response.GetMovieCreditsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMovieDetailsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMovieVideosByIdResponse
import gustavo.projects.moviemanager.network.response.person.GetPersonDetailsByIdResponse
import gustavo.projects.moviemanager.network.response.person.GetPersonImagesByIdResponse
import gustavo.projects.moviemanager.network.response.person.GetPersonsMoviesByIdResponse
import gustavo.projects.moviemanager.network.services.MovieDbService
import kotlin.Exception
import retrofit2.Response

class ApiClient(
    private val movieDbService: MovieDbService
) {

    suspend fun getMovieById(
        movie_ID: Int,
        language: String
    ): SimpleResponse<GetMovieDetailsByIdResponse> {
        return safeApiCall { movieDbService.getMovieDetailsById(movie_ID, language) }
    }

    suspend fun getUpcomingMoviesPage(
        pageIndex: Int,
        language: String
    ): SimpleResponse<MovieList> {
        return safeApiCall { movieDbService.getUpcomingMoviesPage(pageIndex, language) }
    }

    suspend fun getNowPlayingMoviesPage(
        pageIndex: Int,
        language: String
    ): SimpleResponse<MovieList> {
        return safeApiCall { movieDbService.getNowPlayingMoviesPage(pageIndex, language) }
    }

    suspend fun getTopRatedMoviesPage(
        pageIndex: Int,
        language: String
    ): SimpleResponse<MovieList> {
        return safeApiCall { movieDbService.getTopRatedMoviesPage(pageIndex, language) }
    }

    suspend fun getPopularMoviesPage(
        pageIndex: Int,
        language: String
    ): SimpleResponse<MovieList> {
        return safeApiCall { movieDbService.getPopularMoviesPage(pageIndex, language) }
    }

    suspend fun getMovieCreditsById(movie_ID: Int): SimpleResponse<GetMovieCreditsByIdResponse> {
        return safeApiCall { movieDbService.getMovieCreditsById(movie_ID) }
    }

    suspend fun getMovieVideosById(movie_ID: Int): SimpleResponse<GetMovieVideosByIdResponse> {
        return safeApiCall { movieDbService.getMovieVideosById(movie_ID) }
    }

    suspend fun getMoviesPageByTitle(
        movieTitle: String,
        pageIndex: Int,
        language: String
    ): SimpleResponse<MovieList> {
        return safeApiCall { movieDbService.getMoviesPageByTitle(movieTitle, pageIndex, language) }
    }

    // PEOPLE

    suspend fun getPersonDetailsById(
        people_ID: Int,
        language: String
    ): SimpleResponse<GetPersonDetailsByIdResponse> {
        return safeApiCall { movieDbService.getPersonDetailsById(people_ID, language) }
    }

    suspend fun getPersonsMoviesById(
        people_ID: Int,
        language: String
    ): SimpleResponse<GetPersonsMoviesByIdResponse> {
        return safeApiCall { movieDbService.getPersonsMoviesById(people_ID, language) }
    }

    suspend fun getPersonImagesById(people_ID: Int): SimpleResponse<GetPersonImagesByIdResponse> {
        return safeApiCall { movieDbService.getPersonImagesById(people_ID) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
