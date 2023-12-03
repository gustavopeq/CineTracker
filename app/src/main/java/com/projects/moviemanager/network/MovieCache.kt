package com.projects.moviemanager.network

import com.projects.moviemanager.domain.models.MovieDetails

object MovieCache {

    val movieMap = mutableMapOf<Int, MovieDetails>()
}