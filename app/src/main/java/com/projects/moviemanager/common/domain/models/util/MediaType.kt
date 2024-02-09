package com.projects.moviemanager.common.domain.models.util

enum class MediaType {
    MOVIE,
    SHOW,
    PERSON,
    UNKNOWN;

    companion object {
        fun getType(typeName: String?): MediaType {
            return when (typeName?.lowercase()) {
                "movie" -> MOVIE
                "show", "tv" -> SHOW
                "person", "people" -> PERSON
                else -> UNKNOWN
            }
        }
    }
}
