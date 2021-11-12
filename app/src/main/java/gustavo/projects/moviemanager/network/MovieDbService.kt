package gustavo.projects.moviemanager.network

import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.network.response.GetMovieDetailsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMovieCreditsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMoviesPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbService {


    @GET("movie/{movie_ID}?api_key=${Constants.API_KEY}")
    suspend fun getMovieDetailsById(
            @Path("movie_ID") movieId : Int
    ): Response<GetMovieDetailsByIdResponse>

    @GET("movie/now_playing?api_key=${Constants.API_KEY}")
    suspend fun getNowPlayingMoviesPage(
        @Query("page") pageIndex: Int
    ) : Response<GetMoviesPageResponse>

    @GET("movie/popular?api_key=${Constants.API_KEY}")
    suspend fun getPopularMoviesPage(
        @Query("page") pageIndex: Int
    ) : Response<GetMoviesPageResponse>

    @GET("search/movie?api_key=${Constants.API_KEY}")
    suspend fun getMoviesPageByTitle(
        @Query("query") movieTitle: String,
        @Query("page") pageIndex: Int
    ) : Response<GetMoviesPageResponse>

    @GET("movie/{movie_ID}/credits?api_key=${Constants.API_KEY}")
    suspend fun getMovieCreditsById(
            @Path("movie_ID") movieId: Int
    ): Response<GetMovieCreditsByIdResponse>


}