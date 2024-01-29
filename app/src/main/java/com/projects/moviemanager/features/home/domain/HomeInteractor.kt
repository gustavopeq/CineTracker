package com.projects.moviemanager.features.home.domain

import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.domain.models.content.toGenericSearchContent
import com.projects.moviemanager.network.repository.home.HomeRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

class HomeInteractor @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun getTrendingMulti(): List<GenericSearchContent> {
        val result = homeRepository.getTrendingMulti()

        var listResults: List<GenericSearchContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getTrendingMulti failed with error: ${response.error}")
                }
                is Left -> {
                    listResults = response.value.results.mapNotNull {
                        it.toGenericSearchContent()
                    }
                }
            }
        }
        return listResults
    }
}
