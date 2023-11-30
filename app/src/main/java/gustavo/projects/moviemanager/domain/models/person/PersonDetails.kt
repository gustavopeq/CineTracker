package gustavo.projects.moviemanager.domain.models.person

import gustavo.projects.moviemanager.domain.models.movie.Movie

data class PersonDetails(
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val id: Int?,
    val name: String?,
    val place_of_birth: String?,
    val profile_path: String?,
    val movieInList: List<Movie>?,
    val personImageList: List<PersonImages.Profile?>?
)
