package com.projects.moviemanager.network.response.content

import com.projects.moviemanager.compose.common.MediaType

data class MovieApiResponse(
    override val id: Int,
    override val title: String,
    override val vote_average: Double,
    override val poster_path: String,
    override val backdrop_path: String?,
    override val genre_ids: List<Int?>?,
    override val original_language: String?,
    override val original_title: String?,
    override val overview: String?,
    override val popularity: Double?,
    override val release_date: String?,
    override val vote_count: Int?,
    override val mediaType: MediaType = MediaType.MOVIE,

    val video: Boolean?,
    val adult: Boolean?
) : BaseMediaContentResponse
