package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.network.models.content.common.BaseContentResponse
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse

data class GenericSearchContent(
    val id: Int,
    val name: String?,
    val posterPath: String?,
    val mediaType: MediaType
)

fun BaseContentResponse.toGenericSearchContent(): GenericSearchContent? {
    val mediaType = when (this) {
        is MovieResponse -> MediaType.MOVIE
        is ShowResponse -> MediaType.SHOW
        is PersonResponse -> MediaType.PERSON
        is MultiResponse -> MediaType.getType(this.media_type)
        else -> { MediaType.UNKNOWN }
    }

    if (mediaType == MediaType.UNKNOWN || poster_path.isNullOrEmpty()) return null

    return GenericSearchContent(
        id = this.id,
        name = this.title,
        posterPath = this.poster_path,
        mediaType = mediaType
    )
}
