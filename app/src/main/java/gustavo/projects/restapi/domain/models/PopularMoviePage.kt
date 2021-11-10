package gustavo.projects.restapi.domain.models


data class PopularMoviePage(
        val page: Int = 0,
        val results: List<PopularMovie> = emptyList()
)