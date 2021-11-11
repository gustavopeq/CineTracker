package gustavo.projects.moviemanager.domain.mappers

import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.domain.models.MoviePage
import gustavo.projects.moviemanager.network.response.GetMoviesPageResponse

object MoviePageMapper {

    fun buildFrom(response: GetMoviesPageResponse): MoviePage {

        // Create list of popular movie using only the necessary fields
        val listOfMovies = mutableListOf<Movie>()
        response.results.forEach {
            listOfMovies.add(Movie(it.vote_average, it.id, it.poster_path, it.title))
        }

        return MoviePage(response.page, listOfMovies)
    }
}