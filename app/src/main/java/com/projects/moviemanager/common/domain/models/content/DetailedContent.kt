package com.projects.moviemanager.common.domain.models.content

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.util.UiConstants.EMPTY_RATINGS
import com.projects.moviemanager.network.models.content.common.ContentGenre
import com.projects.moviemanager.network.models.content.common.MovieResponse
import com.projects.moviemanager.network.models.content.common.PersonResponse
import com.projects.moviemanager.network.models.content.common.ProductionCountry
import com.projects.moviemanager.network.models.content.common.ShowResponse

interface GenericContentInterface {
    val id: Int
    val name: String
    val rating: Double
    val overview: String
    val posterPath: String
    val backdropPath: String
    val mediaType: MediaType
}

data class DetailedContent(
    override val id: Int,
    override val name: String,
    override val rating: Double,
    override val overview: String,
    override val posterPath: String,
    override val backdropPath: String,
    override val mediaType: MediaType,
    val productionCountries: List<ProductionCountry?>? = emptyList(),
    val genres: List<ContentGenre?>? = emptyList(),
    val runtime: Int? = 0,
    val releaseDate: String? = "",
    val budget: Long? = 0,
    val revenue: Long? = 0,
    val firstAirDate: String? = "",
    val lastAirDate: String? = "",
    val birthday: String? = "",
    val deathday: String? = "",
    val placeOfBirth: String? = "",
    val numberOfSeasons: Int = 0,
    val numberOfEpisodes: Int = 0
) : GenericContentInterface

fun MovieResponse.toDetailedContent(): DetailedContent {
    return DetailedContent(
        id = this.id,
        name = this.title.orEmpty(),
        overview = this.overview.orEmpty(),
        posterPath = this.poster_path.orEmpty(),
        backdropPath = this.backdrop_path.orEmpty(),
        mediaType = MediaType.MOVIE,
        rating = this.vote_average ?: EMPTY_RATINGS,
        productionCountries = this.production_countries,
        genres = this.genres,
        runtime = this.runtime,
        releaseDate = this.release_date.orEmpty(),
        budget = this.budget,
        revenue = this.revenue
    )
}

fun ShowResponse.toDetailedContent(): DetailedContent {
    return DetailedContent(
        id = this.id,
        name = this.name.orEmpty(),
        overview = this.overview.orEmpty(),
        posterPath = this.poster_path.orEmpty(),
        backdropPath = this.backdrop_path.orEmpty(),
        mediaType = MediaType.SHOW,
        rating = this.vote_average ?: EMPTY_RATINGS,
        productionCountries = this.production_countries,
        genres = this.genres,
        firstAirDate = this.first_air_date,
        lastAirDate = this.last_air_date,
        numberOfSeasons = this.number_of_seasons ?: 0,
        numberOfEpisodes = this.number_of_episodes ?: 0
    )
}

fun PersonResponse.toDetailedContent(): DetailedContent {
    return DetailedContent(
        id = this.id,
        name = this.name.orEmpty(),
        overview = this.biography.orEmpty(),
        posterPath = this.profile_path.orEmpty(),
        backdropPath = this.backdrop_path.orEmpty(),
        mediaType = MediaType.PERSON,
        rating = this.vote_average ?: EMPTY_RATINGS,
        birthday = this.birthday,
        deathday = this.deathday,
        placeOfBirth = this.place_of_birth
    )
}
