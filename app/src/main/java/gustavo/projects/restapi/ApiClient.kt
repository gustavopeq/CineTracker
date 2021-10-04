package gustavo.projects.restapi

import retrofit2.Response
import kotlin.Exception

class ApiClient(
    private val movieDbService: MovieDbService
    ) {

    suspend fun getMovieById(movie_ID: Int): SimpleResponse<GetMovieByIdResponse>{
        return safeApiCall { movieDbService.getMovieById(movie_ID) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>) : SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e: Exception){
            SimpleResponse.failure(e)
        }
    }
}
