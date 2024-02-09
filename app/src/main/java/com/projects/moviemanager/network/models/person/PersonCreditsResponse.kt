package com.projects.moviemanager.network.models.person

import com.projects.moviemanager.network.models.content.common.CastResponse

data class PersonCreditsResponse(
    val cast: List<CastResponse>?,
    val crew: List<CrewResponse?>?,
    val id: Int?
)
