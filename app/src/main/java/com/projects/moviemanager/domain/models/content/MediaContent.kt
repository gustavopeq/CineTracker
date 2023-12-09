package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.network.response.content.BaseMediaContentResponse

data class MediaContent(
    override val id: Int,
    override val title: String,
    override val vote_average: Double,
    override val poster_path: String,
    override val mediaType: MediaType
) : BaseMediaContent

fun BaseMediaContentResponse.toMediaContent(): MediaContent {
    return MediaContent(
        id = this.id,
        title = this.title,
        vote_average = this.vote_average,
        poster_path = this.poster_path,
        mediaType = this.mediaType ?: MediaType.MOVIE
    )
}