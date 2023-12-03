package com.projects.moviemanager.network.response

data class GetMovieCreditsByIdResponse(
        val id: Int = 0,
        val cast: List<GetMovieCastByIdResponse> = emptyList()
)