package gustavo.projects.moviemanager.network

import gustavo.projects.moviemanager.domain.models.MovieDetails

object MovieCache {

    val movieMap = mutableMapOf<Int, MovieDetails>()
}