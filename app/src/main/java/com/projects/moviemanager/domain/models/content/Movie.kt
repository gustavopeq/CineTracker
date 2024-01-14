package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.common.domain.MediaType

data class Movie(
    override val id: Int,
    override val title: String,
    val vote_average: Double,
    override val overview: String,
    override val poster_path: String,
    override val mediaType: MediaType
) : BaseMediaContent

