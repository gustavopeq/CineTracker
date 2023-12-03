package gustavo.projects.moviemanager.network.services.show

import gustavo.projects.moviemanager.network.response.content.ContentListPageResponse
import gustavo.projects.moviemanager.network.response.content.ShowApiResponse
import gustavo.projects.moviemanager.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowService {
    @GET("tv/{content_list_type}?api_key=${Constants.API_KEY}")
    suspend fun getShowList(
        @Path("content_list_type") contentListType: String,
        @Query("page") pageIndex: Int,
        @Query("language") language: String
    ): Response<ContentListPageResponse<ShowApiResponse>>
}
