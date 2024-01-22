package com.projects.moviemanager.network.models.person

data class PersonCreditsResponse(
    val cast: List<CastResponse>?,
    val crew: List<CrewResponse?>?,
    val id: Int?
)
