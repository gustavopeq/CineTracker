package com.projects.moviemanager.network.services

import com.projects.moviemanager.network.response.person.GetPersonImagesByIdResponse
import com.projects.moviemanager.network.response.person.PersonCreditsResponse
import com.projects.moviemanager.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbService {

    // PEOPLE

    @GET("person/{person_ID}/movie_credits?api_key=${Constants.API_KEY}")
    suspend fun getPersonsMoviesById(
        @Path("person_ID") personId: Int,
        @Query("language") language: String
    ): Response<PersonCreditsResponse>

    @GET("person/{person_ID}/images?api_key=${Constants.API_KEY}")
    suspend fun getPersonImagesById(
        @Path("person_ID") personId: Int
    ): Response<GetPersonImagesByIdResponse>
}
