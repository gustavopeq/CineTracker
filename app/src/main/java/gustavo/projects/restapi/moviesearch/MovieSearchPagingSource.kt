package gustavo.projects.restapi.moviesearch

import androidx.paging.PagingSource
import androidx.paging.PagingState
import gustavo.projects.restapi.domain.mappers.PopularMovieMapper
import gustavo.projects.restapi.domain.models.PopularMovie
import gustavo.projects.restapi.network.NetworkLayer

class MovieSearchPagingSource(
    private val userSearch: String,
    private val searchExceptionCallback: (SearchException) -> Unit
) : PagingSource<Int, PopularMovie>() {

    sealed class SearchException(
        val title: String,
        val description: String = ""
    ): Exception() {
        object EmptySearch : SearchException(
            title = "Type to search!"
        )
        object NoResults : SearchException(
            title = "Sorry... :(",
            description = "No results found"
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovie> {

        if (userSearch.isEmpty()) {
            val exception = SearchException.EmptySearch
            searchExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        val pageNumber = params.key ?: 1
        var previousKey: Int?
        if(pageNumber == 1) {
            previousKey = null
        }else {
            previousKey = pageNumber - 1
        }

        val request = NetworkLayer.apiClient.getMoviesPageByTitle(userSearch, pageNumber)

        //When search doesn't find any movie
        if (request.body.page == 1 && request.body.results.isEmpty()) {
            val exception = SearchException.NoResults
            searchExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        request.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = request.body.results.map { movieResponse ->
                PopularMovieMapper.buildFrom(movieResponse)
            },
            prevKey = previousKey,
            nextKey = pageNumber + 1
        )
    }


    override fun getRefreshKey(state: PagingState<Int, PopularMovie>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}