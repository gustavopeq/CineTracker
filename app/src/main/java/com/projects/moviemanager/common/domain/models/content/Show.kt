package com.projects.moviemanager.common.domain.models.content

import com.projects.moviemanager.common.domain.models.util.MediaType

data class Show(
    override val id: Int,
    override val overview: String,
    override val poster_path: String,
    override val title: String,
    override val mediaType: MediaType,
    val vote_average: Double
) : BaseMediaContent

