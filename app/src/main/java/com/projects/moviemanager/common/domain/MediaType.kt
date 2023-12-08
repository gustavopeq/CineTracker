package com.projects.moviemanager.common.domain

enum class MediaType {
    MOVIE,
    SHOW;

    companion object {
        fun getType(typeName: String?): MediaType {
            return values().find { it.name == typeName } ?: MOVIE
        }
    }
}