package com.projects.moviemanager.common.domain

import timber.log.Timber

enum class MediaType {
    MOVIE,
    SHOW,
    PERSON,
    UNKNOWN;

    companion object {
        fun getType(typeName: String?): MediaType {
            Timber.tag("print").d("gettingType: $typeName")
            return when (typeName?.lowercase()) {
                "movie" -> MOVIE
                "show", "tv" -> SHOW
                "person", "people" -> PERSON
                else -> UNKNOWN
            }
        }
    }
}
