package com.projects.moviemanager.network.response.person

data class PersonCreditsResponse(
    val cast: List<CastResponse>?,
    val crew: List<CrewResponse?>?,
    val id: Int?
)
