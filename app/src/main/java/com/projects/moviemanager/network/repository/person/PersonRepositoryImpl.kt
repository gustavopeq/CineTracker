package com.projects.moviemanager.network.repository.person

import com.projects.moviemanager.network.models.ApiError
import com.projects.moviemanager.network.response.person.PersonDetailsResponse
import com.projects.moviemanager.network.services.person.PersonService
import com.projects.moviemanager.network.util.Either
import com.projects.moviemanager.network.util.asFlow
import com.projects.moviemanager.network.util.toApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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
}
