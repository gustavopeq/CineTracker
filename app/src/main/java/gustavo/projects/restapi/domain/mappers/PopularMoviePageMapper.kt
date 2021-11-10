package gustavo.projects.restapi.domain.mappers

import gustavo.projects.restapi.domain.models.PopularMovie
import gustavo.projects.restapi.domain.models.PopularMoviePage
import gustavo.projects.restapi.network.response.GetPopularMoviesPageResponse

object PopularMoviePageMapper {

    fun buildFrom(response: GetPopularMoviesPageResponse): PopularMoviePage {

        // Create list of popular movie using only the necessary fields
        val listPopularMovie = mutableListOf<PopularMovie>()
        response.results.forEach {
            listPopularMovie.add(PopularMovie(it.vote_average, it.id, it.poster_path, it.title))
        }

        return PopularMoviePage(response.page, listPopularMovie)
    }
}