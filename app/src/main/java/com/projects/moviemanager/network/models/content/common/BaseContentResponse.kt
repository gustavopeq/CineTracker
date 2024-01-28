package com.projects.moviemanager.network.models.content.common

import com.squareup.moshi.Json

interface BaseContentResponse {
    val id: Int
    val adult: Boolean?
    val popularity: Double?
    val poster_path: String?
    val title: String?
    val original_title: String?
}

data class MultiResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val original_title: String?,
    override val popularity: Double?,
    override val poster_path: String?,
    override val title: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val media_type: String?,
    val original_language: String?,
    val overview: String?,
    val release_date: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    val profile_path: String?
) : BaseContentResponse

data class MovieResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val original_title: String?,
    override val popularity: Double?,
    override val poster_path: String?,
    override val title: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val overview: String?,
    val release_date: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) : BaseContentResponse

data class ShowResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val popularity: Double?,
    override val poster_path: String?,
    @Json(name = "name")
    override val title: String?,
    @Json(name = "original_name")
    override val original_title: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val overview: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val first_air_date: String?,
    val origin_country: List<String>?
) : BaseContentResponse

data class PersonResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val popularity: Double?,
    @Json(name = "name")
    override val title: String?,
    @Json(name = "original_name")
    override val original_title: String?,
    @Json(name = "profile_path")
    override val poster_path: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val gender: Int?,
    val known_for_department: String?,
    val known_for: List<MultiResponse>
) : BaseContentResponse
