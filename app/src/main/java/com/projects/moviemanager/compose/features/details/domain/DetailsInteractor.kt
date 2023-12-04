package com.projects.moviemanager.compose.features.details.domain

import com.projects.moviemanager.domain.models.content.MediaContentDetails
import com.projects.moviemanager.domain.models.content.toMediaContentDetails
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import timber.log.Timber
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovieDetailsById(
        movieId: Int
    ): MediaContentDetails? {
        val result = movieRepository.getMovieDetailsById(movieId)
        var movieDetails: MediaContentDetails? = null
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getMovieDetailsById failed with error: ${response.error}")
                }
                is Left -> {
                    movieDetails = response.value.toMediaContentDetails()
                }
            }
        }
        return movieDetails
    }
}