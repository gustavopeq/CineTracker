package gustavo.projects.moviemanager.network.services.movie

import gustavo.projects.moviemanager.network.response.content.ContentListPageResponse
import gustavo.projects.moviemanager.network.response.content.MovieApiResponse
import gustavo.projects.moviemanager.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/{movie_list_type}?api_key=${Constants.API_KEY}")
    suspend fun getMovieList(
        @Path("movie_list_type") movieListType: String,
        @Query("page") pageIndex: Int,
        @Query("language") language: String
    ): Response<ContentListPageResponse<MovieApiResponse>>
}
