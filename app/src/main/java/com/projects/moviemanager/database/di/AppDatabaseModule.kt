package com.projects.moviemanager.database.di

import android.content.Context
import androidx.room.Room
import com.projects.moviemanager.R
import com.projects.moviemanager.database.AppDatabase
import com.projects.moviemanager.database.migration.MIGRATION_1_2
import com.projects.moviemanager.database.migration.MIGRATION_2_3
import com.projects.moviemanager.database.migration.MIGRATION_3_4
import com.projects.moviemanager.database.migration.MIGRATION_4_5
import com.projects.moviemanager.database.roomCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_manager_database"
        )
            .addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4,
                MIGRATION_4_5(getLocalizedListNames(context))
            )
            .addCallback(roomCallback)
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun getLocalizedListNames(
        context: Context
    ): Map<String, String> {
        return mapOf(
            "watchlist" to context.getString(R.string.watchlist_tab).lowercase(),
            "watched" to context.getString(R.string.watched_tab).lowercase()
        )
    }
}
