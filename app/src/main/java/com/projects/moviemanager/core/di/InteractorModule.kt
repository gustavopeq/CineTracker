package com.projects.moviemanager.core.di

import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.database.repository.DatabaseRepositoryImpl
import com.projects.moviemanager.features.browse.domain.BrowseInteractor
import com.projects.moviemanager.features.details.domain.DetailsInteractor
import com.projects.moviemanager.features.home.domain.HomeInteractor
import com.projects.moviemanager.features.search.domain.SearchInteractor
import com.projects.moviemanager.features.watchlist.domain.WatchlistInteractor
import com.projects.moviemanager.network.repository.home.HomeRepository
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.person.PersonRepository
import com.projects.moviemanager.network.repository.search.SearchRepository
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
        personRepository: PersonRepository,
        databaseRepository: DatabaseRepository
    ): DetailsInteractor {
        return DetailsInteractor(
            movieRepository = movieRepository,
            showRepository = showRepository,
            personRepository = personRepository,
            databaseRepository = databaseRepository
        )
    }

    @Singleton
    @Provides
    fun provideWatchlistInteractor(
        databaseRepository: DatabaseRepository,
        movieRepository: MovieRepository,
        showRepository: ShowRepository
    ): WatchlistInteractor {
        return WatchlistInteractor(
            databaseRepository = databaseRepository,
            movieRepository = movieRepository,
            showRepository = showRepository
        )
    }

    @Singleton
    @Provides
    fun provideSearchInteractor(
        searchRepository: SearchRepository
    ): SearchInteractor {
        return SearchInteractor(
            searchRepository = searchRepository
        )
    }

    @Singleton
    @Provides
    fun provideHomeInteractor(
        homeRepository: HomeRepository,
        databaseRepository: DatabaseRepository,
        movieRepository: MovieRepository,
        showRepository: ShowRepository
    ): HomeInteractor {
        return HomeInteractor(
            homeRepository = homeRepository,
            databaseRepository = databaseRepository,
            movieRepository = movieRepository,
            showRepository = showRepository
        )
    }
}
