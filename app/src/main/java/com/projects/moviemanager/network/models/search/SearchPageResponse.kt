package com.projects.moviemanager.network.models.search

data class SearchPageResponse(
    val page: Int,
    val results: List<SearchItemResponse>,
    val total_pages: Int,
    val total_results: Int
)

data class SearchItemResponse(
    val adult: Boolean,
    val backdrop_path: String?,
    val id: Int,
    val title: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String,
    val poster_path: String,
    val media_type: String,
    val genre_ids: List<Int>,
    val popularity: Double?,
    val release_date: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
)
