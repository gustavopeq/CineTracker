package com.projects.moviemanager.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.features.watchlist.model.DefaultLists

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE content_entity (
                contentEntityDbId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                contentId INTEGER NOT NULL,
                mediaType TEXT NOT NULL
            )
        """
        )
        val defaultMediaType = MediaType.MOVIE.name
        db.execSQL(
            "INSERT INTO content_entity (contentId, mediaType) " +
                "SELECT dbId, '$defaultMediaType' FROM item_entity"
        )
        db.execSQL("DROP TABLE item_entity")
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        val defaultListId = DefaultLists.WATCHLIST.listId

        db.execSQL(
            """
            ALTER TABLE content_entity 
            ADD COLUMN listId TEXT NOT NULL DEFAULT '$defaultListId'
        """
        )
    }
}

val MIGRATION_3_4: Migration = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE content_entity ADD COLUMN createdAt INTEGER DEFAULT 0 NOT NULL")
    }
}
