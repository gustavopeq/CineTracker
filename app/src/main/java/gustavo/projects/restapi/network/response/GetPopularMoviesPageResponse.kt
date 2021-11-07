package gustavo.projects.restapi.network.response

data class GetPopularMoviesPageResponse(
    val page: Int = 0,
    val results: List<GetPopularMovieByIdResponse> = emptyList()
)
