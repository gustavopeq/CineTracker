package gustavo.projects.restapi

import retrofit2.Response

class ApiClient(
    private val movieDbService: MovieDbService
    ) {

    suspend fun getMovieById(movie_ID: Int): Response<GetMovieByIdResponse>{
        return movieDbService.getMovieById(movie_ID)
    }
}
