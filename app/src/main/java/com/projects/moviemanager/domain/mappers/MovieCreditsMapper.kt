package com.projects.moviemanager.domain.mappers

import com.projects.moviemanager.domain.models.MovieCast
import com.projects.moviemanager.domain.models.MovieCredits
import com.projects.moviemanager.network.response.ContentCreditsResponse

object MovieCreditsMapper {

    fun buildFrom(response: ContentCreditsResponse): MovieCredits {

        val listOfCast = mutableListOf<MovieCast>()

        response.cast.forEach {
            listOfCast.add(MovieCast(it.character, it.id, it.name, it.profile_path))
        }

        return MovieCredits(response.id, listOfCast)
    }
}