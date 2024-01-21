package com.projects.moviemanager.domain.models.person

import com.projects.moviemanager.network.models.person.PersonProfileResponse

data class PersonImage(
    val aspectRatio: Double?,
    val filePath: String?,
    val height: Int?,
    val width: Int?
)

fun PersonProfileResponse.toPersonImage(): PersonImage {
    return PersonImage(
        aspectRatio = this.aspect_ratio,
        filePath = this.file_path,
        height = this.height,
        width = this.width
    )
}
