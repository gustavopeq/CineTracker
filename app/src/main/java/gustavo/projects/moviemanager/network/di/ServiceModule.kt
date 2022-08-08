package gustavo.projects.moviemanager.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gustavo.projects.moviemanager.network.ApiClient
import gustavo.projects.moviemanager.network.MovieDbService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): ApiClient {
        val movieDbService = retrofit.create(MovieDbService::class.java)

        return ApiClient(movieDbService)
    }

}