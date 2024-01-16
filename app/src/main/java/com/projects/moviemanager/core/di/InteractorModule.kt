package com.projects.moviemanager.core.di

import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.database.repository.DatabaseRepositoryImpl
import com.projects.moviemanager.features.browse.domain.BrowseInteractor
import com.projects.moviemanager.features.details.domain.DetailsInteractor
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.person.PersonRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Singleton
    @Provides
    fun provideDetailsInteractor(
        movieRepository: MovieRepository,
        showRepository: ShowRepository,
        personRepository: PersonRepository
    ): DetailsInteractor {
        return DetailsInteractor(
            movieRepository = movieRepository,
            showRepository = showRepository,
            personRepository = personRepository
        )
    }

    @Singleton
    @Provides
    fun provideWatchlistInteractor(
        databaseRepository: DatabaseRepository
    ): WatchlistInteractor {
        return WatchlistInteractor(
            databaseRepository = databaseRepository
        )
    }
}
