package com.projects.moviemanager.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.database.repository.DatabaseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesMainViewModel(
        databaseRepository: DatabaseRepository
    ): MainViewModel {
        return MainViewModel(databaseRepository)
    }
}
