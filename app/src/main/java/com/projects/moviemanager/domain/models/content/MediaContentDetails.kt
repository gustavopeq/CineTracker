package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.ContentGenre
import com.projects.moviemanager.domain.models.ProductionCountry
import com.projects.moviemanager.network.response.content.BaseMediaContentResponse

data class MediaContentDetails(
    override val id: Int,
    override val title: String,
    override val vote_average: Double,
    override val poster_path: String,
    override val mediaType: MediaType,
    val backdrop_path: String?,
    val overview: String?,
    val production_countries: List<ProductionCountry?>?,
    val release_date: String?,
    val genres: List<ContentGenre?>?,
    val runtime: Int?
) : BaseMediaContent

fun BaseMediaContentResponse.toMediaContentDetails(): MediaContentDetails {
    return MediaContentDetails(
        id = this.id,
        title = this.title,
        vote_average = this.vote_average,
        poster_path = this.poster_path.orEmpty(),
        mediaType = this.mediaType ?: MediaType.MOVIE,
        backdrop_path = this.backdrop_path,
        overview = this.overview,
        production_countries = this.production_countries,
        release_date = this.release_date,
        genres = this.genres,
        runtime = this.runtime
    )
}