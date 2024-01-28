package com.projects.moviemanager.network.di

import com.projects.moviemanager.network.repository.home.HomeRepository
import com.projects.moviemanager.network.repository.home.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.movie.MovieRepositoryImpl
import com.projects.moviemanager.network.repository.person.PersonRepository
import com.projects.moviemanager.network.repository.person.PersonRepositoryImpl
import com.projects.moviemanager.network.repository.search.SearchRepository
import com.projects.moviemanager.network.repository.search.SearchRepositoryImpl
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.repository.show.ShowRepositoryImpl
import com.projects.moviemanager.network.services.home.HomeService
import com.projects.moviemanager.network.services.movie.MovieService
import com.projects.moviemanager.network.services.person.PersonService
import com.projects.moviemanager.network.services.search.SearchService
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

    @Singleton
    @Provides
    fun providePersonRepository(
        personService: PersonService
    ): PersonRepository {
        return PersonRepositoryImpl(personService)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(
        searchService: SearchService
    ): SearchRepository {
        return SearchRepositoryImpl(searchService)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(
        homeService: HomeService
    ): HomeRepository {
        return HomeRepositoryImpl(homeService)
    }
}
