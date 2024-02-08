package com.projects.moviemanager.network.models.person

import com.projects.moviemanager.network.models.content.common.PersonResponse

data class PersonCreditsResponse(
    val cast: List<PersonResponse>?,
    val crew: List<CrewResponse?>?,
    val id: Int?
)
