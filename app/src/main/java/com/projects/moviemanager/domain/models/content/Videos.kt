package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.network.response.content.VideoResponse

data class Videos(
    val name: String,
    val key: String,
    val publishedAt: String
)

fun VideoResponse.toVideos(): Videos {
    return Videos(
        name = this.name,
        key = this.key,
        publishedAt = this.published_at
    )
}
