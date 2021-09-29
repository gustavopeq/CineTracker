package gustavo.projects.restapi

import android.widget.Toast

class SharedRepository {

    suspend fun getMovieById(movie_ID: Int) : GetMovieByIdResponse? {

        val request = NetworkLayer.apiClient.getMovieById(movie_ID)

        if(request.isSuccessful){
            return request.body()!!
        }

        return null
    }
}
