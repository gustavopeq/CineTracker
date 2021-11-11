package gustavo.projects.moviemanager.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gustavo.projects.moviemanager.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_MOVIEDB)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val movieDbService: MovieDbService by lazy{
        retrofit.create(MovieDbService::class.java)
    }

    val apiClient = ApiClient(movieDbService)
}