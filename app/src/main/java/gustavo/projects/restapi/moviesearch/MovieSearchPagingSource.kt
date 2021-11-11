package gustavo.projects.restapi.moviesearch

import androidx.paging.PagingSource
import androidx.paging.PagingState
import gustavo.projects.restapi.domain.models.MovieDetails

class MovieSearchPagingSource : PagingSource<Int, MovieDetails>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetails> {
        val pageNumber = params.key ?: 1
        var previousKey: Int?
        if(pageNumber == 1) {
            previousKey = null
        }else {
            previousKey = pageNumber - 1
        }

        TODO()
    }


    override fun getRefreshKey(state: PagingState<Int, MovieDetails>): Int? {
        TODO("Not yet implemented")
    }

}