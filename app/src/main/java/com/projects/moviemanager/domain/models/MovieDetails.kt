package com.projects.moviemanager.domain.models

import com.projects.moviemanager.network.response.content.VideoResponse

data class MovieDetails(
    val budget: Int?,
    val genres: List<ContentGenre?>?,
    val id: Int?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val title: String?,
    val vote_average: Double?,
    val movieCast: List<MovieCast?>?,
    val movieVideos: List<VideoResponse?>?,
    val productionCountry: List<ProductionCountry?>?
)