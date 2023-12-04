package com.projects.moviemanager.network.response.content

import com.projects.moviemanager.compose.common.MediaType
import com.projects.moviemanager.domain.models.ContentGenre
import com.projects.moviemanager.domain.models.ProductionCompany
import com.projects.moviemanager.domain.models.ProductionCountry
import com.projects.moviemanager.domain.models.SpokenLanguage
import com.squareup.moshi.Json

data class ShowApiResponse(
    override val id: Int,
    @Json(name = "name")
    override val title: String,
    override val vote_average: Double,
    override val poster_path: String,
    override val backdrop_path: String?,
    override val genre_ids: List<Int?>?,
    override val original_language: String?,
    @Json(name = "original_name")
    override val original_title: String?,
    override val overview: String?,
    override val popularity: Double?,
    @Json(name = "first_air_date")
    override val release_date: String?,
    override val vote_count: Int?,
    override val mediaType: MediaType? = MediaType.SHOW,
    override val adult: Boolean?,
    override val genres: List<ContentGenre?>?,
    override val homepage: String?,
    override val production_companies: List<ProductionCompany?>?,
    override val production_countries: List<ProductionCountry?>?,
    override val spoken_languages: List<SpokenLanguage?>?,
    override val runtime: Int?,

    val origin_country: List<String>?
) : BaseMediaContentResponse
