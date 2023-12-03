package gustavo.projects.moviemanager.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gustavo.projects.moviemanager.network.ApiClient
import gustavo.projects.moviemanager.network.services.MovieDbService
import gustavo.projects.moviemanager.network.services.movie.MovieService
import gustavo.projects.moviemanager.network.services.show.ShowService
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMovieApiService(retrofit: Retrofit): ApiClient {
        val movieDbService = retrofit.create(MovieDbService::class.java)

        return ApiClient(movieDbService)
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Singleton
    @Provides
    fun provideShowService(retrofit: Retrofit): ShowService {
        return retrofit.create(ShowService::class.java)
    }
}
