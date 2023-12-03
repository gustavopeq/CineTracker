package com.projects.moviemanager.network.response.content

data class ContentListPageResponse<T : BaseMediaContentResponse> (
    val page: Int = 0,
    val results: List<T> = emptyList()
)
