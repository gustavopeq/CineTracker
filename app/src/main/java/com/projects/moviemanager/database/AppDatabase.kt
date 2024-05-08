package com.projects.moviemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.model.ContentEntity
import com.projects.moviemanager.database.model.ListEntity

@Database(
    entities = [ContentEntity::class, ListEntity::class],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentEntityDao(): ContentEntityDao
}
