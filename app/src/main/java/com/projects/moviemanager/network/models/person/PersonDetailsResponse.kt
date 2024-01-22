package com.projects.moviemanager.network.models.person

data class PersonDetailsResponse(
    val id: Int,
    val name: String,
    val profile_path: String?,
    val adult: Boolean?,
    val also_known_as: List<String?>?,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val gender: Int?,
    val homepage: Any?,
    val imdb_id: String?,
    val known_for_department: String?,
    val place_of_birth: String?,
    val popularity: Double?
)
