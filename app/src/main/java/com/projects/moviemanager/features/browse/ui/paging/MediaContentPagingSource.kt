package com.projects.moviemanager.features.browse.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.content.toMovieDetailsInfo
import com.projects.moviemanager.domain.models.content.toShowDetailsInfo
import com.projects.moviemanager.domain.models.util.ContentListType
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import kotlinx.coroutines.flow.first
import timber.log.Timber

class MediaContentPagingSource(
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository,
    private val contentListType: ContentListType,
    private val mediaType: MediaType
) : PagingSource<Int, DetailedMediaInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailedMediaInfo> {
        return try {
            val pageNumber = params.key ?: 1
            val previousKey = if (pageNumber == 1) {
                null
            } else {
                pageNumber - 1
            }

            val apiResponse = when (mediaType) {
                MediaType.MOVIE -> movieRepository.getMovieList(contentListType, pageNumber).first()
                MediaType.SHOW -> {
                    showRepository.getShowList(contentListType.type, pageNumber).first()
                }
                MediaType.PERSON -> {
                    throw IllegalStateException("Invalid media type for paging source: $mediaType")
                }
            }

            return when (apiResponse) {
                is Right -> {
                    Timber.e("Paging source error: ${apiResponse.error.exception}")
                    LoadResult.Error(
                        apiResponse.error.exception ?: Exception("Unknown error")
                    )
                }

                is Left -> {
                    val data = apiResponse.value.results.map {
                        when (it.mediaType) {
                            MediaType.MOVIE -> it.toMovieDetailsInfo()
                            MediaType.SHOW -> it.toShowDetailsInfo()
                            else -> {
                                throw IllegalStateException(
                                    "Invalid media type for paging source: $mediaType"
                                )
                            }
                        }
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

    override fun getRefreshKey(state: PagingState<Int, DetailedMediaInfo>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
}
