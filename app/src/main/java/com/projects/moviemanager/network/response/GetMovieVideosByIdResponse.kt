package com.projects.moviemanager.network.response

import com.projects.moviemanager.domain.models.MovieVideo

data class GetMovieVideosByIdResponse(
    val id: Int?,
    val results: List<MovieVideo?>?
)