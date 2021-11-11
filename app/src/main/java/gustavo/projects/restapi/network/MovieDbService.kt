package gustavo.projects.restapi.network

import gustavo.projects.restapi.util.Constants
import gustavo.projects.restapi.network.response.GetMovieByIdResponse
import gustavo.projects.restapi.network.response.GetMovieCreditsByIdResponse
import gustavo.projects.restapi.network.response.GetPopularMoviesPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbService {


    @GET("movie/{movie_ID}?api_key=${Constants.API_KEY}")
    suspend fun getMovieById(
            @Path("movie_ID") movieId : Int
    ): Response<GetMovieByIdResponse>

    @GET("movie/popular?api_key=${Constants.API_KEY}")
    suspend fun getPopularMoviesPage(
        @Query("page") pageIndex: Int
    ) : Response<GetPopularMoviesPageResponse>

    @GET("search/movie?api_key=${Constants.API_KEY}")
    suspend fun getMoviesPageByTitle(
        @Query("query") movieTitle: String,
        @Query("page") pageIndex: Int
    ) : Response<GetPopularMoviesPageResponse>

    @GET("movie/{movie_ID}/credits?api_key=${Constants.API_KEY}")
    suspend fun getMovieCreditsById(
            @Path("movie_ID") movieId: Int
    ): Response<GetMovieCreditsByIdResponse>

}