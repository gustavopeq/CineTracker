package com.projects.moviemanager.network.response.content

import com.squareup.moshi.Json
import com.projects.moviemanager.compose.common.MediaType

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
    override val mediaType: MediaType = MediaType.SHOW,

    val origin_country: List<String>?
) : BaseMediaContentResponse
