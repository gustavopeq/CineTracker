package com.projects.moviemanager.features.browse.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.domain.models.util.ContentListType
import com.projects.moviemanager.features.browse.ui.paging.MediaContentPagingSource
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.util.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BrowseInteractor @Inject constructor(
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository
) {
    fun getMediaContentListPager(
        contentListType: ContentListType,
        mediaType: MediaType
    ): Flow<PagingData<DetailedMediaInfo>> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            MediaContentPagingSource(
                movieRepository,
                showRepository,
                contentListType,
                mediaType
            )
        }.flow
    }
}
