package com.projects.moviemanager.features.search.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.features.search.ui.paging.SearchPagingSource
import com.projects.moviemanager.network.repository.search.SearchRepository
import com.projects.moviemanager.common.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchInteractor @Inject constructor(
    private val searchRepository: SearchRepository
) {
    fun onSearchQuery(
        query: String,
        mediaType: MediaType?
    ): Flow<PagingData<GenericContent>> {
        return Pager(PagingConfig(pageSize = Constants.PAGE_SIZE)) {
            SearchPagingSource(
                searchRepository,
                query,
                mediaType
            )
        }.flow
    }
}
