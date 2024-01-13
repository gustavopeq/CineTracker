package com.projects.moviemanager.network.repository.person

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.response.person.PersonCreditsResponse
import com.projects.moviemanager.network.response.person.PersonDetailsResponse
import com.projects.moviemanager.network.services.person.PersonService
import com.projects.moviemanager.network.util.Either
import com.projects.moviemanager.network.util.asFlow
import com.projects.moviemanager.network.util.toApiResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PersonRepositoryImpl @Inject constructor(
    private val personService: PersonService
) : PersonRepository {
    override suspend fun getPersonDetailsById(
        personId: Int
    ): Flow<Either<PersonDetailsResponse, ApiError>> {
        return toApiResult {
            personService.getPersonDetailsById(personId)
        }.asFlow()
    }

    override suspend fun getPersonCreditsById(
        personId: Int
    ): Flow<Either<PersonCreditsResponse, ApiError>> {
        return toApiResult {
            personService.getPersonCreditsById(personId)
        }.asFlow()
    }
}
