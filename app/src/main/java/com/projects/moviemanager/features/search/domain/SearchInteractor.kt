package com.projects.moviemanager.features.search.domain

import com.projects.moviemanager.network.repository.search.SearchRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import timber.log.Timber
import javax.inject.Inject

class SearchInteractor @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun onSearchQuery(
        query: String,
        page: Int
    ) {
        val result = searchRepository.onSearchQuery(
            query = query,
            page = page
        )

        result.collect { response ->
            when (response) {
                is Right -> Timber.tag("print").d("error: ${response.error}")
                is Left -> {
                    Timber.tag("print").d("Response: ${response.value.results}")
                }
            }
        }
    }
}

