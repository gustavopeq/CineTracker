package com.projects.moviemanager.network

import com.projects.moviemanager.network.response.person.GetPersonDetailsByIdResponse
import com.projects.moviemanager.network.response.person.GetPersonImagesByIdResponse
import com.projects.moviemanager.network.response.person.GetPersonsMoviesByIdResponse
import com.projects.moviemanager.network.services.MovieDbService
import retrofit2.Response

class ApiClient(
    private val movieDbService: MovieDbService
) {

    // PEOPLE

    suspend fun getPersonDetailsById(
        people_ID: Int,
        language: String
    ): SimpleResponse<GetPersonDetailsByIdResponse> {
        return safeApiCall { movieDbService.getPersonDetailsById(people_ID, language) }
    }

    suspend fun getPersonsMoviesById(
        people_ID: Int,
        language: String
    ): SimpleResponse<GetPersonsMoviesByIdResponse> {
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
