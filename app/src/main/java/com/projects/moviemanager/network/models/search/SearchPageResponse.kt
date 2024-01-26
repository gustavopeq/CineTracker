package com.projects.moviemanager.network.models.search

import com.projects.moviemanager.network.models.content.common.BaseContentResponse

data class SearchPageResponse<T : BaseContentResponse>(
    val page: Int,
    val results: List<T> = emptyList(),
    val total_pages: Int,
    val total_results: Int
)

