package com.projects.moviemanager.network.response

data class ContentCreditsResponse(
    val id: Int = 0,
    val cast: List<ContentCastResponse> = emptyList()
)
