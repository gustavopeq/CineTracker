package com.projects.moviemanager.network.models.content.common

import com.projects.moviemanager.network.models.content.common.VideoResponse

data class VideosByIdResponse(
    val id: Int?,
    val results: List<VideoResponse>
)
