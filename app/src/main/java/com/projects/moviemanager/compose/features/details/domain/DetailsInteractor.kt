package com.projects.moviemanager.compose.features.details.domain

import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.domain.models.content.ContentCast
import com.projects.moviemanager.domain.models.content.MediaContentDetails
import com.projects.moviemanager.domain.models.content.toContentCast
import com.projects.moviemanager.domain.models.content.toMediaContentDetails
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import timber.log.Timber
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository
) {
    suspend fun getContentDetailsById(
        contentId: Int,
        mediaType: MediaType
    ): MediaContentDetails? {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieDetailsById(contentId)
            MediaType.SHOW -> showRepository.getShowDetailsById(contentId)
        }

        var contentDetails: MediaContentDetails? = null
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentDetailsById failed with error: ${response.error}")
                }
                is Left -> {
                    contentDetails = response.value.toMediaContentDetails()
                }
            }
        }
        return contentDetails
    }

    suspend fun getContentCreditsById(
        contentId: Int,
        mediaType: MediaType
    ): List<ContentCast> {
        val result = movieRepository.getMovieCreditsById(contentId)
//            when (mediaType) {
//            MediaType.MOVIE -> movieRepository.getMovieCreditsById(contentId)
//            MediaType.SHOW -> ""
//        }

        var contentCastList:  List<ContentCast> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentCreditsById failed with error: ${response.error}")
                }
                is Left -> {
                    contentCastList = response.value.cast.map {
                        it.toContentCast()
                    }
                }
            }
        }
        return contentCastList
    }
}