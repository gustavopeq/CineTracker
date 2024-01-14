package com.projects.moviemanager.common.domain

enum class MediaType {
    MOVIE,
    SHOW,
    PERSON;

    companion object {
        fun getType(typeName: String?): MediaType {
            return values().find { it.name == typeName } ?: MOVIE
        }
    }
}
