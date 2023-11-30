package gustavo.projects.moviemanager.compose.features.browse.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import gustavo.projects.moviemanager.domain.models.movie.Movie
import gustavo.projects.moviemanager.domain.models.movie.toDomain
import gustavo.projects.moviemanager.domain.models.util.MovieListType
import gustavo.projects.moviemanager.network.repository.movie.MovieRepository
import gustavo.projects.moviemanager.network.util.Left
import gustavo.projects.moviemanager.network.util.Right
import kotlinx.coroutines.flow.first

class MoviePagingSource(
    private val movieRepository: MovieRepository,
    private val movieListType: MovieListType
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1
            val previousKey = if (pageNumber == 1) {
                null
            } else {
                pageNumber - 1
            }
            val apiResponse =
                movieRepository.getMovieList(movieListType, pageNumber).first()

            return when (apiResponse) {
                is Right -> {
                    LoadResult.Error(
                        apiResponse.error.exception ?: Exception("Unknown error")
                    )
                }

                is Left -> {
                    LoadResult.Page(
                        data = apiResponse.value.results.mapNotNull {
                            it.toDomain()
                        },
                        prevKey = previousKey,
                        nextKey = pageNumber + 1
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
}
