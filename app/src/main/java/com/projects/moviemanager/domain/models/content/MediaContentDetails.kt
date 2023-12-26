package com.projects.moviemanager.domain.models.content

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.domain.models.ContentGenre
import com.projects.moviemanager.domain.models.ProductionCountry
import com.projects.moviemanager.network.response.content.BaseMediaContentResponse
import com.projects.moviemanager.network.response.person.PersonDetailsResponse
sealed class DetailedMediaInfo : BaseMediaContent

data class MovieDetailsInfo(
    override val id: Int,
    override val title: String,
    override val overview: String,
    override val poster_path: String,
    override val mediaType: MediaType,
    val voteAverage: Double?,
    val productionCountries: List<ProductionCountry?>?,
    val genres: List<ContentGenre?>?,
    val runtime: Int?,
    val releaseDate: String?
) : DetailedMediaInfo()

data class ShowDetailsInfo(
    override val id: Int,
    override val title: String,
    override val overview: String,
    override val poster_path: String,
    override val mediaType: MediaType,
    val voteAverage: Double?,
    val productionCountries: List<ProductionCountry?>?,
    val genres: List<ContentGenre?>?
) : DetailedMediaInfo()

data class PersonDetailsInfo(
    override val id: Int,
    override val title: String,
    override val overview: String,
    override val poster_path: String,
    override val mediaType: MediaType,
    val birthday: String?,
    val deathday: String?,
    val placeOfBirth: String?
) : DetailedMediaInfo()

fun BaseMediaContentResponse.toMovieDetailsInfo(): DetailedMediaInfo {
    return MovieDetailsInfo(
        id = this.id,
        title = this.title,
        overview = this.overview.orEmpty(),
        poster_path = this.poster_path.orEmpty(),
        mediaType = this.mediaType ?: MediaType.MOVIE,
        voteAverage = this.vote_average,
        productionCountries = this.production_countries,
        genres = this.genres,
        runtime = this.runtime,
        releaseDate = this.release_date
    )
}

fun BaseMediaContentResponse.toShowDetailsInfo(): DetailedMediaInfo {
    return ShowDetailsInfo(
        id = this.id,
        title = this.title,
        overview = this.overview.orEmpty(),
        poster_path = this.poster_path.orEmpty(),
        mediaType = this.mediaType ?: MediaType.SHOW,
        voteAverage = this.vote_average,
        productionCountries = this.production_countries,
        genres = this.genres
    )
}

fun PersonDetailsResponse.toPersonDetailsInfo(): DetailedMediaInfo {
    return PersonDetailsInfo(
        id = this.id,
        title = this.name,
        overview = this.biography.orEmpty(),
        poster_path = this.profile_path.orEmpty(),
        mediaType = MediaType.PERSON,
        birthday = this.birthday,
        deathday = this.deathday,
        placeOfBirth = this.place_of_birth
    )
}
