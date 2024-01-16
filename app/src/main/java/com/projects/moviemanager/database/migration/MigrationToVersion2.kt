package com.projects.moviemanager.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projects.moviemanager.common.domain.MediaType

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE content_entity (
                contentEntityDbId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                contentId INTEGER NOT NULL,
                mediaType TEXT NOT NULL
            )
        """)
        val defaultMediaType = MediaType.MOVIE.name
        db.execSQL("INSERT INTO content_entity (contentId, mediaType) SELECT dbId, '$defaultMediaType' FROM item_entity")
        db.execSQL("DROP TABLE item_entity")
    }
}
