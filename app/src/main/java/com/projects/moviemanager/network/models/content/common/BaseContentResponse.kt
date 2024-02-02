package com.projects.moviemanager.network.models.content.common

interface BaseContentResponse {
    val id: Int
    val adult: Boolean?
    val popularity: Double?
    val poster_path: String?
    val profile_path: String?
    val backdrop_path: String?
    val title: String?
    val name: String?
    val original_title: String?
    val original_name: String?
    val vote_average: Double?
    val overview: String?
}

data class MultiResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val original_title: String?,
    override val popularity: Double?,
    override val poster_path: String?,
    override val profile_path: String?,
    override val backdrop_path: String?,
    override val title: String?,
    override val name: String?,
    override val original_name: String?,
    override val vote_average: Double?,
    override val overview: String?,
    val genre_ids: List<Int>?,
    val media_type: String?,
    val original_language: String?,
    val release_date: String?,
    val video: Boolean?,
    val vote_count: Int?
) : BaseContentResponse

data class MovieResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val original_title: String?,
    override val popularity: Double?,
    override val poster_path: String?,
    override val profile_path: String?,
    override val backdrop_path: String?,
    override val title: String?,
    override val name: String?,
    override val original_name: String?,
    override val vote_average: Double?,
    override val overview: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val release_date: String?,
    val video: Boolean?,
    val vote_count: Int?
) : BaseContentResponse

data class ShowResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val popularity: Double?,
    override val poster_path: String?,
    override val profile_path: String?,
    override val backdrop_path: String?,
    override val title: String?,
    override val original_title: String?,
    override val name: String?,
    override val original_name: String?,
    override val vote_average: Double?,
    override val overview: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val vote_count: Int?,
    val first_air_date: String?,
    val origin_country: List<String>?
) : BaseContentResponse

data class PersonResponse(
    override val id: Int,
    override val adult: Boolean?,
    override val popularity: Double?,
    override val title: String?,
    override val original_title: String?,
    override val poster_path: String?,
    override val backdrop_path: String?,
    override val profile_path: String?,
    override val name: String?,
    override val original_name: String?,
    override val vote_average: Double?,
    override val overview: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val gender: Int?,
    val known_for_department: String?,
    val known_for: List<MultiResponse>
) : BaseContentResponse
