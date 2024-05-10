package com.projects.moviemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.dao.ListEntityDao
import com.projects.moviemanager.database.model.ContentEntity
import com.projects.moviemanager.database.model.ListEntity

@Database(
    entities = [ContentEntity::class, ListEntity::class],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contentEntityDao(): ContentEntityDao
    abstract fun listEntityDao(): ListEntityDao
}

val roomCallback = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        // Execute the SQL to insert the default lists
        val defaultLists = listOf("watchlist", "watched")
        defaultLists.forEach { listName ->
            db.execSQL(
                """
                INSERT INTO list_entity (listName)
                VALUES (?)
                """,
                arrayOf(listName)
            )
        }
    }
}
