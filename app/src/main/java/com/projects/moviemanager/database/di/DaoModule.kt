package com.projects.moviemanager.database.di

import com.projects.moviemanager.database.AppDatabase
import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.dao.ListEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun provideItemEntityDao(
        appDatabase: AppDatabase
    ): ContentEntityDao {
        return appDatabase.contentEntityDao()
    }

    @Provides
    @Singleton
    fun provideListEntityDao(
        appDatabase: AppDatabase
    ): ListEntityDao {
        return appDatabase.listEntityDao()
    }
}
