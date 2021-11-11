package gustavo.projects.moviemanager.network.response

data class GetMoviesPageResponse(
    val page: Int = 0,
    val results: List<GetMoviesByIdResponse> = emptyList()
)
