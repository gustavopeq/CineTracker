package com.projects.moviemanager.network.response.content

data class ContentCreditsResponse(
    val id: Int = 0,
    val cast: List<ContentCastResponse> = emptyList()
)
