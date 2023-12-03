package com.projects.moviemanager.compose.features.browse.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.compose.features.browse.ui.paging.MediaContentPagingSource
import com.projects.moviemanager.domain.models.content.BaseMediaContent
import com.projects.moviemanager.domain.models.util.ContentListType
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.util.Constants.PAGE_SIZE
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class BrowseInteractor @Inject constructor(
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository
) {
    fun getMediaContentListPager(
        contentListType: ContentListType,
        mediaType: MediaType
    ): Flow<PagingData<BaseMediaContent>> {
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
