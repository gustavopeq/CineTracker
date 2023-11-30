package gustavo.projects.moviemanager.domain.models.util

enum class MovieListType(val type: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming")
}
