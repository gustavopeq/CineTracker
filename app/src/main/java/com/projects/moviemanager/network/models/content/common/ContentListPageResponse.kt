package com.projects.moviemanager.network.models.content.common

data class ContentListPageResponse<T : BaseContentResponse> (
    val page: Int = 0,
    val results: List<T> = emptyList()
)
