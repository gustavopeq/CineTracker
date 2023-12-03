package com.projects.moviemanager.domain.models.person

import com.projects.moviemanager.domain.models.content.Movie

data class PersonMoviesIn(
    val cast: List<Movie>,
    val id: Int
)
