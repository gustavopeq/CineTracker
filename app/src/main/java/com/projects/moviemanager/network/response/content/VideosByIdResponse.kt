package com.projects.moviemanager.network.response.content

data class VideosByIdResponse(
    val id: Int?,
    val results: List<VideoResponse>
)
