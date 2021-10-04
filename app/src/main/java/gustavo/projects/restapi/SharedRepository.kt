package gustavo.projects.restapi


class SharedRepository {

    suspend fun getMovieById(movie_ID: Int) : GetMovieByIdResponse? {

        val request = NetworkLayer.apiClient.getMovieById(movie_ID)

        if(request.failed) {
            return null
        }

        if(!request.isSuccessful){
            return null
        }

        return request.body
    }
}
