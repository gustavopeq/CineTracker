package com.projects.moviemanager.compose.features.browse.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.projects.moviemanager.compose.features.browse.domain.BrowseInteractor
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Singleton
    @Provides
    fun provideBrowseInteractor(
        movieRepository: MovieRepository,
        showRepository: ShowRepository
    ): BrowseInteractor {
        return BrowseInteractor(
            movieRepository = movieRepository,
            showRepository = showRepository
        )
    }
}
