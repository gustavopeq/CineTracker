package com.projects.moviemanager.network.services

import com.projects.moviemanager.network.response.ContentCreditsResponse
import com.projects.moviemanager.network.response.GetMovieVideosByIdResponse
import com.projects.moviemanager.network.response.person.GetPersonDetailsByIdResponse
import com.projects.moviemanager.network.response.person.GetPersonImagesByIdResponse
import com.projects.moviemanager.network.response.person.GetPersonsMoviesByIdResponse
import com.projects.moviemanager.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbService {

    @GET("movie/{movie_ID}/credits?api_key=${Constants.API_KEY}")
    suspend fun getMovieCreditsById(
        @Path("movie_ID") movieId: Int
    ): Response<ContentCreditsResponse>

    @GET("movie/{movie_ID}/videos?api_key=${Constants.API_KEY}")
    suspend fun getMovieVideosById(
        @Path("movie_ID") movieId: Int
    ): Response<GetMovieVideosByIdResponse>

    // PEOPLE

    @GET("person/{person_ID}?api_key=${Constants.API_KEY}")
    suspend fun getPersonDetailsById(
        @Path("person_ID") personId: Int,
        @Query("language") language: String
    ): Response<GetPersonDetailsByIdResponse>

    @GET("person/{person_ID}/movie_credits?api_key=${Constants.API_KEY}")
    suspend fun getPersonsMoviesById(
        @Path("person_ID") personId: Int,
        @Query("language") language: String
    ): Response<GetPersonsMoviesByIdResponse>

    @GET("person/{person_ID}/images?api_key=${Constants.API_KEY}")
    suspend fun getPersonImagesById(
        @Path("person_ID") personId: Int
    ): Response<GetPersonImagesByIdResponse>
}
