package gustavo.projects.restapi.network

import gustavo.projects.restapi.network.response.GetMovieByIdResponse
import gustavo.projects.restapi.network.response.GetPopularMoviesPageResponse
import retrofit2.Response
import kotlin.Exception

class ApiClient(
    private val movieDbService: MovieDbService
    ) {

    suspend fun getMovieById(movie_ID: Int): SimpleResponse<GetMovieByIdResponse> {
        return safeApiCall { movieDbService.getMovieById(movie_ID) }
    }

    suspend fun getPopularMoviesPage(pageIndex: Int): SimpleResponse<GetPopularMoviesPageResponse>{
        return safeApiCall { movieDbService.getPopularMoviesPage(pageIndex) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>) : SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e: Exception){
            SimpleResponse.failure(e)
        }
    }
}
