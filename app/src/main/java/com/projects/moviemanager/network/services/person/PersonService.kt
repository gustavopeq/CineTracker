package com.projects.moviemanager.network.services.person

import com.projects.moviemanager.network.models.person.PersonCreditsResponse
import com.projects.moviemanager.network.models.person.PersonDetailsResponse
import com.projects.moviemanager.network.models.person.PersonImagesResponse
import com.projects.moviemanager.common.util.Constants
import com.projects.moviemanager.common.util.Constants.ENGLISH_LANGUAGE_CODE
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

    @GET("person/{person_ID}/combined_credits?api_key=${Constants.API_KEY}")
    suspend fun getPersonCreditsById(
        @Path("person_ID") personId: Int,
        @Query("language") language: String = ENGLISH_LANGUAGE_CODE
    ): Response<PersonCreditsResponse>

    @GET("person/{person_ID}/images?api_key=${Constants.API_KEY}")
    suspend fun getPersonImagesById(
        @Path("person_ID") personId: Int
    ): Response<PersonImagesResponse>
}
