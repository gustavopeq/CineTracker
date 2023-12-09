package com.projects.moviemanager.network.response.content

data class VideoResponse(
    val name: String,
    val key: String,
    val published_at: String,
    val id: String?,
    val iso_3166_1: String?,
    val iso_639_1: String?,
    val official: Boolean?,
    val site: String?,
    val size: Int?,
    val type: String?
)
