package com.projects.moviemanager.features.search.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.content.toGenericContent
import com.projects.moviemanager.network.repository.search.SearchRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import kotlinx.coroutines.flow.first
import timber.log.Timber

class SearchPagingSource(
    private val searchRepository: SearchRepository,
    private val query: String,
    private val mediaType: MediaType?
) : PagingSource<Int, GenericContent>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GenericContent> {
        return try {
            val pageNumber = params.key ?: 1
            val previousKey = if (pageNumber == 1) {
                null
            } else {
                pageNumber - 1
            }

            val apiResponse = when (mediaType) {
                MediaType.MOVIE -> {
                    searchRepository.onSearchMovieByQuery(
                        query = query,
                        page = pageNumber
                    ).first()
                }
                MediaType.SHOW -> {
                    searchRepository.onSearchShowByQuery(
                        query = query,
                        page = pageNumber
                    ).first()
                }
                MediaType.PERSON -> {
                    searchRepository.onSearchPersonByQuery(
                        query = query,
                        page = pageNumber
                    ).first()
                }
                else -> {
                    searchRepository.onSearchMultiByQuery(
                        query = query,
                        page = pageNumber
                    ).first()
                }
            }

            return when (apiResponse) {
                is Right -> {
                    Timber.e("Search Paging source error: ${apiResponse.error}")
                    LoadResult.Error(
                        apiResponse.error.exception ?: Exception("Unknown error")
                    )
                }

                is Left -> {
                    val data = apiResponse.value.results.mapNotNull {
                        it.toGenericContent()
                    }
                    LoadResult.Page(
                        data = data,
                        prevKey = previousKey,
                        nextKey = pageNumber + 1
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GenericContent>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
}
