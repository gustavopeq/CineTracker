package gustavo.projects.restapi.domain.models

data class Movie(
        val budget: Int?,
        val genres: List<MovieGenre?>?,
        val id: Int?,
        val overview: String?,
        val poster_path: String?,
        val release_date: String?,
        val revenue: Int?,
        val runtime: Int?,
        val title: String?,
        val vote_average: Double?,
) {
}