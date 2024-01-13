package com.projects.moviemanager.network

import com.projects.moviemanager.network.response.person.GetPersonImagesByIdResponse
import com.projects.moviemanager.network.response.person.PersonCreditsResponse
import com.projects.moviemanager.network.services.MovieDbService
import retrofit2.Response

class ApiClient(
    private val movieDbService: MovieDbService
) {

    // PEOPLE

    suspend fun getPersonsMoviesById(
        people_ID: Int,
        language: String
    ): SimpleResponse<PersonCreditsResponse> {
        return safeApiCall { movieDbService.getPersonsMoviesById(people_ID, language) }
    }

    suspend fun getPersonImagesById(people_ID: Int): SimpleResponse<GetPersonImagesByIdResponse> {
        return safeApiCall { movieDbService.getPersonImagesById(people_ID) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}
