package com.projects.moviemanager.common.domain.models.person

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.network.models.content.common.MultiResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse

data class PersonDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val mediaType: MediaType,
    val birthday: String?,
    val deathday: String?,
    val placeOfBirth: String?,
    val knownForDepartment: String?,
    val knownFor: List<MultiResponse>
)

fun PersonResponse.toPersonDetails(): PersonDetails {
    return PersonDetails(
        id = this.id,
        title = this.name.orEmpty(),
        overview = this.overview.orEmpty(),
        posterPath = this.profile_path.orEmpty(),
        mediaType = MediaType.PERSON,
        birthday = null,
        deathday = null,
        placeOfBirth = null,
        knownForDepartment = this.known_for_department,
        knownFor = this.known_for.orEmpty()
    )
}