package com.projects.moviemanager.network.response.content

data class ContentCastResponse(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String?,
    val adult: Boolean?,
    val cast_id: Int?,
    val credit_id: String?,
    val gender: Int?,
    val known_for_department: String?,
    val order: Int?,
    val original_name: String?,
    val popularity: Double?
)
