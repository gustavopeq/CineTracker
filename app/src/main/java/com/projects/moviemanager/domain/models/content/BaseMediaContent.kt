package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.compose.common.MediaType

interface BaseMediaContent {
    val id: Int
    val title: String
    val vote_average: Double
    val poster_path: String
    val mediaType: MediaType
}