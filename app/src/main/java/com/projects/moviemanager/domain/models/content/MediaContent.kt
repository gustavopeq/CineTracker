package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.network.response.content.BaseMediaContentResponse
import com.projects.moviemanager.network.response.person.CastResponse

data class MediaContent(
    override val id: Int,
    override val title: String,
    override val overview: String,
    override val poster_path: String,
    override val mediaType: MediaType,
    val vote_average: Double? = 0.0
) : BaseMediaContent

fun BaseMediaContentResponse.toMediaContent(): MediaContent {
    return MediaContent(
        id = this.id,
        title = this.title,
        overview = this.overview.orEmpty(),
        vote_average = this.vote_average,
        poster_path = this.poster_path.orEmpty(),
        mediaType = this.mediaType ?: MediaType.MOVIE
    )
}

fun List<CastResponse>?.toMediaContentList(): List<MediaContent> {
    return this?.map { castResponse ->
        MediaContent(
            id = castResponse.id,
            title = castResponse.title,
            poster_path = castResponse.poster_path.orEmpty(),
            overview = castResponse.overview.orEmpty(),
            mediaType = castResponse.mediaType,
            vote_average = castResponse.vote_average
        )
    } ?: emptyList()
}