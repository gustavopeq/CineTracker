package gustavo.projects.restapi.network.response

data class GetMovieCreditsByIdResponse(
        val id: Int = 0,
        val cast: List<GetMovieCastByIdResponse> = emptyList()
)