package com.projects.moviemanager.features.details.domain

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.content.ContentCast
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.domain.models.content.MediaContentDetails
import com.projects.moviemanager.domain.models.content.Videos
import com.projects.moviemanager.domain.models.content.toContentCast
import com.projects.moviemanager.domain.models.content.toMediaContent
import com.projects.moviemanager.domain.models.content.toMediaContentDetails
import com.projects.moviemanager.domain.models.content.toVideos
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import javax.inject.Inject
import timber.log.Timber

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
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieCreditsById(contentId)
            MediaType.SHOW -> showRepository.getShowCreditsById(contentId)
        }

        var contentCastList: List<ContentCast> = emptyList()
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

    suspend fun getContentVideosById(
        contentId: Int,
        mediaType: MediaType
    ): List<Videos> {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieVideosById(contentId)
            MediaType.SHOW -> showRepository.getShowVideosById(contentId)
        }

        var videoList: List<Videos> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentVideosById failed with error: ${response.error}")
                }
                is Left -> {
                    videoList = response.value.results.map {
                        it.toVideos()
                    }
                }
            }
        }

        return videoList
    }

    suspend fun getSimilarContentById(
        contentId: Int,
        mediaType: MediaType
    ): List<MediaContent> {
        val result = movieRepository.getSimilarMoviesById(contentId)

        var listOfSimilar: List<MediaContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getSimilarContentById failed with error: ${response.error}")
                }
                is Left -> {
                    listOfSimilar = response.value.results.map {
                        it.toMediaContent()
                    }
                }
            }
        }
        return listOfSimilar
    }
}
