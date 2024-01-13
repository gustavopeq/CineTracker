package com.projects.moviemanager.network.response.person

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.ContentGenre
import com.projects.moviemanager.domain.models.ProductionCompany
import com.projects.moviemanager.domain.models.ProductionCountry
import com.projects.moviemanager.domain.models.SpokenLanguage
import com.projects.moviemanager.network.response.content.BaseMediaContentResponse
import com.squareup.moshi.Json

data class CastResponse(
    override val id: Int,
    override val vote_average: Double,
    override val poster_path: String?,
    override val backdrop_path: String?,
    override val genre_ids: List<Int?>?,
    override val original_language: String?,
    override val original_title: String?,
    override val overview: String?,
    override val popularity: Double?,
    override val release_date: String?,
    override val vote_count: Int?,
    val media_type: String?,
    @Json(name = "title")
    val _title: String?,
    val name: String?,

    override val adult: Boolean?,
    override val genres: List<ContentGenre?>?,
    override val homepage: String?,
    override val production_companies: List<ProductionCompany?>?,
    override val production_countries: List<ProductionCountry?>?,
    override val spoken_languages: List<SpokenLanguage?>?,
    override val runtime: Int?
) : BaseMediaContentResponse {
    override val mediaType: MediaType
        get() = when (media_type) {
            "tv" -> MediaType.SHOW
            else -> MediaType.MOVIE
        }
    override val title: String
        get() = _title ?: name.orEmpty()
}
