package com.projects.moviemanager.database.di

import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.database.repository.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseRepositoryModule {
    @Singleton
    @Provides
    fun provideDatabaseRepository(
        contentEntityDao: ContentEntityDao
    ): DatabaseRepository {
        return DatabaseRepositoryImpl(contentEntityDao)
    }
}