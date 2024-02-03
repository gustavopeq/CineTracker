package com.projects.moviemanager.network.models.content.show

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.network.models.content.common.ContentGenre
import com.projects.moviemanager.network.models.content.common.ProductionCompany
import com.projects.moviemanager.network.models.content.common.ProductionCountry
import com.projects.moviemanager.network.models.content.common.SpokenLanguage
import com.projects.moviemanager.network.models.content.common.BaseMediaContentResponse
import com.squareup.moshi.Json

data class ShowApiResponse(
    override val id: Int,
    @Json(name = "name")
    override val title: String,
    override val vote_average: Double,
    override val poster_path: String?,
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
