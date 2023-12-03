package com.projects.moviemanager.domain.models.person

data class PersonImages(
    val id: Int?,
    val imageList: List<Profile?>?
) {
    data class Profile(
            val aspect_ratio: Double?,
            val file_path: String?,
            val height: Int?,
            val width: Int?
    )
}
