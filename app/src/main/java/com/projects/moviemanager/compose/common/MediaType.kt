package com.projects.moviemanager.compose.common

enum class MediaType {
    MOVIE,
    SHOW;

    companion object {
        fun getType(typeName: String?): MediaType {
            return values().find { it.name == typeName } ?: MOVIE
        }
    }
}