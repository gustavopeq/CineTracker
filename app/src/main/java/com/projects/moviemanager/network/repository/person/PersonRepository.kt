package com.projects.moviemanager.network.repository.person

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.response.person.PersonCreditsResponse
import com.projects.moviemanager.network.response.person.PersonDetailsResponse
import com.projects.moviemanager.network.response.person.PersonImagesResponse
import com.projects.moviemanager.network.util.Either
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun getPersonDetailsById(
        personId: Int
    ): Flow<Either<PersonDetailsResponse, ApiError>>

    suspend fun getPersonCreditsById(
        personId: Int
    ): Flow<Either<PersonCreditsResponse, ApiError>>

    suspend fun getPersonImagesById(
        personId: Int
    ): Flow<Either<PersonImagesResponse, ApiError>>
}
