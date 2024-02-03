package com.projects.moviemanager.common.domain.models.content

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.network.models.content.common.BaseContentResponse
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ShowResponse
import com.projects.moviemanager.network.models.content.movie.MovieApiResponse
import com.projects.moviemanager.network.models.content.show.ShowApiResponse

data class GenericContent(
    val id: Int,
    val name: String?,
    val rating: Double?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val mediaType: MediaType
)

fun BaseContentResponse.toGenericSearchContent(): GenericContent? {
    val posterPath: String? = this.poster_path ?: this.profile_path
    val backdropPath: String? = this.backdrop_path
    val name: String? = this.title ?: this.name
    val mediaType = when (this) {
        is MovieResponse -> MediaType.MOVIE
        is ShowResponse -> MediaType.SHOW
        is PersonResponse -> MediaType.PERSON
        is MultiResponse -> MediaType.getType(this.media_type)
        else -> { MediaType.UNKNOWN }
    }

    if (mediaType == MediaType.UNKNOWN || posterPath.isNullOrEmpty()) {
        return null
    }

    return GenericContent(
        id = this.id,
        name = name,
        rating = this.vote_average,
        overview = this.overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType
    )
}

fun MovieApiResponse.toGenericSearchContent(): GenericContent? {
    val mediaType = this.mediaType
    val posterPath = this.poster_path
    val backdropPath = this.backdrop_path

    if (mediaType == MediaType.UNKNOWN || posterPath.isNullOrEmpty()) return null

    return GenericContent(
        id = this.id,
        name = this.title,
        rating = this.vote_average,
        overview = this.overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType
    )
}

fun ShowApiResponse.toGenericSearchContent(): GenericContent? {
    val mediaType = this.mediaType ?: MediaType.UNKNOWN
    val posterPath = this.poster_path
    val backdropPath = this.backdrop_path

    if (mediaType == MediaType.UNKNOWN || posterPath.isNullOrEmpty()) return null

    return GenericContent(
        id = this.id,
        name = this.title,
        rating = this.vote_average,
        overview = this.overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType
    )
}
