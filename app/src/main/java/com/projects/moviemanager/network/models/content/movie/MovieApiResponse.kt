package com.projects.moviemanager.network.models.content.movie

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.network.models.content.common.ContentGenre
import com.projects.moviemanager.network.models.content.common.ProductionCompany
import com.projects.moviemanager.network.models.content.common.ProductionCountry
import com.projects.moviemanager.network.models.content.common.SpokenLanguage
import com.projects.moviemanager.network.models.content.common.BaseMediaContentResponse

data class MovieApiResponse(
    override val id: Int,
    override val title: String,
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
    override val mediaType: MediaType = MediaType.MOVIE,

    val video: Boolean?,
    override val adult: Boolean?,
    override val genres: List<ContentGenre?>?,
    override val homepage: String?,
    override val production_companies: List<ProductionCompany?>?,
    override val production_countries: List<ProductionCountry?>?,
    override val spoken_languages: List<SpokenLanguage?>?,
    override val runtime: Int?
) : BaseMediaContentResponse
