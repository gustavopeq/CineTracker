package gustavo.projects.restapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDbService {


    @GET("movie/{movie_ID}?api_key=1d1b00098614494b1604f69cca5ec62d")
    fun getMovieById(
            @Path("movie_ID") movieId : Int
    ): Call<GetMovieByIdResponse>


}