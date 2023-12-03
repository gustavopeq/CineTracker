package com.projects.moviemanager.network.models

data class ApiError(
    val code: String? = null,
    val exception: Throwable? = null
)
