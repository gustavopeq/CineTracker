package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.network.response.content.BaseMediaContentResponse

data class Show(
    override val vote_average: Double,
    override val id: Int,
    override val poster_path: String,
    override val title: String,
    override val mediaType: MediaType
) : BaseMediaContent

fun BaseMediaContentResponse.toShowDomain(): Show {
    return Show(
        id = this.id,
        title = this.title,
        vote_average = this.vote_average,
        poster_path = this.poster_path,
        mediaType = this.mediaType
    )
}
