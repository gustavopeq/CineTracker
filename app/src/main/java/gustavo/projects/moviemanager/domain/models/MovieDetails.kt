package gustavo.projects.moviemanager.domain.models

data class MovieDetails(
        val budget: Int?,
        val genres: List<MovieGenre?>?,
        val id: Int?,
        val overview: String?,
        val poster_path: String?,
        val release_date: String?,
        val revenue: Long?,
        val runtime: Int?,
        val title: String?,
        val vote_average: Double?,
        val movieCast: List<MovieCast?>?,
        val movieVideos: List<MovieVideo?>?,
        val productionCountry: List<ProductionCountry?>?
)