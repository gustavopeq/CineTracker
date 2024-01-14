package com.projects.moviemanager.network.response.content.common

import com.projects.moviemanager.network.response.content.common.VideoResponse

data class VideosByIdResponse(
    val id: Int?,
    val results: List<VideoResponse>
)
