package com.projects.moviemanager.features.home.domain

import com.projects.moviemanager.domain.models.content.toGenericSearchContent
import com.projects.moviemanager.network.repository.home.HomeRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

class HomeInteractor @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun getTrendingMulti() {
        val result = homeRepository.getTrendingMulti()

        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getTrendingMulti failed with error: ${response.error}")
                }
                is Left -> {
                    val list = response.value.results.mapNotNull {
                        it.toGenericSearchContent()
                    }

                    Timber.tag("print").d("list: $list")
                }
            }
        }
    }
}
