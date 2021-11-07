package gustavo.projects.restapi.network

import gustavo.projects.restapi.network.response.GetMovieByIdResponse
import gustavo.projects.restapi.network.response.GetPopularMovieByIdResponse
import gustavo.projects.restapi.network.response.GetPopularMoviesPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbService {


    @GET("movie/{movie_ID}?api_key=1d1b00098614494b1604f69cca5ec62d")
    suspend fun getMovieById(
            @Path("movie_ID") movieId : Int
    ): Response<GetMovieByIdResponse>

    @GET("movie/popular?api_key=1d1b00098614494b1604f69cca5ec62d")
    suspend fun getPopularMoviesPage(
        @Query("page") pageIndex: Int
    ) : Response<GetPopularMoviesPageResponse>

}