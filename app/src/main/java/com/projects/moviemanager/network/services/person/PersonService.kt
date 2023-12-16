package com.projects.moviemanager.network.services.person

import com.projects.moviemanager.network.response.person.PersonDetailsResponse
import com.projects.moviemanager.util.Constants
import com.projects.moviemanager.util.Constants.ENGLISH_LANGUAGE_CODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET("person/{person_ID}?api_key=${Constants.API_KEY}")
    suspend fun getPersonDetailsById(
        @Path("person_ID") personId: Int,
        @Query("language") language: String = ENGLISH_LANGUAGE_CODE
    ): Response<PersonDetailsResponse>
}
