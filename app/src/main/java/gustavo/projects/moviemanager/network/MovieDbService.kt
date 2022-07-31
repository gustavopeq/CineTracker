package gustavo.projects.moviemanager.network

import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.network.response.GetMovieDetailsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMovieCreditsByIdResponse
import gustavo.projects.moviemanager.network.response.GetMovieVideosByIdResponse
import gustavo.projects.moviemanager.network.response.GetMoviesPageResponse
import gustavo.projects.moviemanager.network.response.person.GetPersonDetailsByIdResponse
import gustavo.projects.moviemanager.network.response.person.GetPersonImagesByIdResponse
import gustavo.projects.moviemanager.network.response.person.GetPersonsMoviesByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbService {


    @GET("movie/{movie_ID}?api_key=${Constants.API_KEY}")
    suspend fun getMovieDetailsById(
        @Path("movie_ID") movieId : Int,
        @Query("language") language: String
    ): Response<GetMovieDetailsByIdResponse>

    @GET("movie/upcoming?api_key=${Constants.API_KEY}")
    suspend fun getUpcomingMoviesPage(
        @Query("page") pageIndex: Int,
        @Query("language") language : String
    ) : Response<GetMoviesPageResponse>

    @GET("movie/now_playing?api_key=${Constants.API_KEY}")
    suspend fun getNowPlayingMoviesPage(
        @Query("page") pageIndex: Int,
        @Query("language") language : String
    ) : Response<GetMoviesPageResponse>

    @GET("movie/top_rated?api_key=${Constants.API_KEY}")
    suspend fun getTopRatedMoviesPage(
        @Query("page") pageIndex: Int,
        @Query("language") language : String
    ) : Response<GetMoviesPageResponse>

    @GET("movie/popular?api_key=${Constants.API_KEY}")
    suspend fun getPopularMoviesPage(
        @Query("page") pageIndex: Int,
        @Query("language") language : String
    ) : Response<GetMoviesPageResponse>

    @GET("search/movie?api_key=${Constants.API_KEY}")
    suspend fun getMoviesPageByTitle(
        @Query("query") movieTitle: String,
        @Query("page") pageIndex: Int,
        @Query("language") language : String
    ) : Response<GetMoviesPageResponse>

    @GET("movie/{movie_ID}/credits?api_key=${Constants.API_KEY}")
    suspend fun getMovieCreditsById(
        @Path("movie_ID") movieId: Int
    ): Response<GetMovieCreditsByIdResponse>

    @GET("movie/{movie_ID}/videos?api_key=${Constants.API_KEY}")
    suspend fun getMovieVideosById(
            @Path("movie_ID") movieId: Int
    ): Response<GetMovieVideosByIdResponse>

    //PEOPLE

    @GET("person/{person_ID}?api_key=${Constants.API_KEY}")
    suspend fun getPersonDetailsById(
            @Path("person_ID") personId: Int,
            @Query("language") language : String
    ): Response<GetPersonDetailsByIdResponse>

    @GET("person/{person_ID}/movie_credits?api_key=${Constants.API_KEY}")
    suspend fun getPersonsMoviesById(
            @Path("person_ID") personId: Int
    ): Response<GetPersonsMoviesByIdResponse>

    @GET("person/{person_ID}/images?api_key=${Constants.API_KEY}")
    suspend fun getPersonImagesById(
            @Path("person_ID") personId: Int
    ): Response<GetPersonImagesByIdResponse>


}