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
    var mediaType: MediaType = MediaType.UNKNOWN
    var posterPath: String? = this.poster_path
    when (this) {
        is MovieResponse -> mediaType = MediaType.MOVIE
        is ShowResponse -> mediaType = MediaType.SHOW
        is PersonResponse -> mediaType = MediaType.PERSON
        is MultiResponse -> {
            mediaType = MediaType.getType(this.media_type)
            if (mediaType == MediaType.PERSON) {
                posterPath = this.profile_path
            }
        }
    }

    if (mediaType == MediaType.UNKNOWN || posterPath.isNullOrEmpty()) return null

    return GenericSearchContent(
        id = this.id,
        name = this.title,
        posterPath = posterPath,
        mediaType = mediaType
    )
}
