package com.projects.moviemanager.network.models.content.common

data class ContentCastResponse(
    val id: Int,
    val name: String,
    val character: String?,
    val profile_path: String?,
    val adult: Boolean?,
    val credit_id: String?,
    val gender: Int?,
    val known_for_department: String?,
    val order: Int?,
    val original_name: String?,
    val popularity: Double?,
    val roles: List<CastRoles>?
)

data class CastRoles(
    val credit_id: String?,
    val character: String?,
    val episode_count: Int?
)
