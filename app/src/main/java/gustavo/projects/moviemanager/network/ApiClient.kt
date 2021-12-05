package gustavo.projects.moviemanager.network

import gustavo.projects.moviemanager.network.response.GetMovieDetailsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMovieCreditsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMovieVideosByIdResponse
import gustavo.projects.moviemanager.network.response.GetMoviesPageResponse
import gustavo.projects.moviemanager.network.response.person.GetPersonDetailsById
import retrofit2.Response
import kotlin.Exception

class ApiClient(
    private val movieDbService: MovieDbService
    ) {

    suspend fun getMovieById(movie_ID: Int): SimpleResponse<GetMovieDetailsByIdResponse> {
        return safeApiCall { movieDbService.getMovieDetailsById(movie_ID) }
    }

    suspend fun getUpcomingMoviesPage(pageIndex: Int): SimpleResponse<GetMoviesPageResponse> {
        return safeApiCall { movieDbService.getUpcomingMoviesPage(pageIndex) }
    }

    suspend fun getNowPlayingMoviesPage(pageIndex: Int): SimpleResponse<GetMoviesPageResponse> {
        return safeApiCall { movieDbService.getNowPlayingMoviesPage(pageIndex) }
    }

    suspend fun getTopRatedMoviesPage(pageIndex: Int): SimpleResponse<GetMoviesPageResponse> {
        return safeApiCall { movieDbService.getTopRatedMoviesPage(pageIndex) }
    }

    suspend fun getPopularMoviesPage(pageIndex: Int): SimpleResponse<GetMoviesPageResponse> {
        return safeApiCall { movieDbService.getPopularMoviesPage(pageIndex) }
    }

    suspend fun getMovieCreditsById(movie_ID: Int): SimpleResponse<GetMovieCreditsByIdResponse> {
        return safeApiCall { movieDbService.getMovieCreditsById(movie_ID) }
    }

    suspend fun getMovieVideosById(movie_ID: Int): SimpleResponse<GetMovieVideosByIdResponse> {
        return safeApiCall { movieDbService.getMovieVideosById(movie_ID) }
    }

    suspend fun getMoviesPageByTitle(movieTitle: String,
                                     pageIndex: Int): SimpleResponse<GetMoviesPageResponse> {
        return safeApiCall { movieDbService.getMoviesPageByTitle(movieTitle, pageIndex) }
    }

    //PEOPLE

    suspend fun getPersonDetailsById(people_ID: Int): SimpleResponse<GetPersonDetailsById> {
        return safeApiCall { movieDbService.getPersonDetailsById(people_ID) }
    }



    private inline fun <T> safeApiCall(apiCall: () -> Response<T>) : SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e: Exception){
            SimpleResponse.failure(e)
        }
    }
}
