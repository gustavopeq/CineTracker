package gustavo.projects.restapi.network

import gustavo.projects.restapi.domain.models.MovieDetails

object MovieCache {

    val movieMap = mutableMapOf<Int, MovieDetails>()
}