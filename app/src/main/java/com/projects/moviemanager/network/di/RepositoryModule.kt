package com.projects.moviemanager.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.movie.MovieRepositoryImpl
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.repository.show.ShowRepositoryImpl
import com.projects.moviemanager.network.services.movie.MovieService
import com.projects.moviemanager.network.services.show.ShowService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieService: MovieService
    ): MovieRepository {
        return MovieRepositoryImpl(movieService)
    }

    @Singleton
    @Provides
    fun provideShowRepository(
        showService: ShowService
    ): ShowRepository {
        return ShowRepositoryImpl(showService)
    }
}
