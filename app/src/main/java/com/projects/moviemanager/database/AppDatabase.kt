package com.projects.moviemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.projects.moviemanager.database.dao.ItemEntityDao
import com.projects.moviemanager.database.model.ItemEntity

@Database(
    entities = arrayOf(ItemEntity::class),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemEntityDao(): ItemEntityDao
}
