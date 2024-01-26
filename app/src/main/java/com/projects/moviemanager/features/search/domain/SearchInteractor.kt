package com.projects.moviemanager.features.search.domain

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.domain.models.content.toGenericSearchContent
import com.projects.moviemanager.network.repository.search.SearchRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

class SearchInteractor @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun onSearchQuery(
        query: String,
        page: Int,
        mediaType: MediaType?
    ): List<GenericSearchContent> {
        val result = when (mediaType) {
            MediaType.MOVIE -> {
                searchRepository.onSearchMovieByQuery(
                    query = query,
                    page = page
                )
            }
            MediaType.SHOW -> {
                searchRepository.onSearchShowByQuery(
                    query = query,
                    page = page
                )
            }
            MediaType.PERSON -> {
                searchRepository.onSearchPersonByQuery(
                    query = query,
                    page = page
                )
            }
            else -> {
                searchRepository.onSearchMultiByQuery(
                    query = query,
                    page = page
                )
            }
        }

        var responseList: List<GenericSearchContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("onSearchQuery failed with error: ${response.error}")
                }
                is Left -> {
                    responseList = response.value.results.mapNotNull {
                        it.toGenericSearchContent()
                    }
                }
            }
        }
        return responseList
    }
}
