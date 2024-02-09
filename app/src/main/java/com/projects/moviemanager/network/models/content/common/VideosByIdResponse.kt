package com.projects.moviemanager.network.models.content.common

data class VideosByIdResponse(
    val id: Int?,
    val results: List<VideoResponse>
)
