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

val MIGRATION_4_5: Migration = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create a temporary table for ListEntity
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS new_list_entity (
                listId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                listName TEXT NOT NULL
            )
            """
        )

        // Populate the new_list_entity table with unique list names from the old content_entity table
        db.execSQL(
            """
            INSERT INTO new_list_entity (listName)
            SELECT DISTINCT listId FROM content_entity
            """
        )

        // Create a temporary table for ContentEntity
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS new_content_entity (
                contentEntityDbId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                contentId INTEGER NOT NULL,
                mediaType TEXT NOT NULL,
                listId INTEGER NOT NULL,
                createdAt INTEGER NOT NULL,
                FOREIGN KEY(listId) REFERENCES list_entity(listId) ON DELETE CASCADE
            )
            """
        )

        // Copy and convert the listId in content_entity to integer referencing new_list_entity
        db.execSQL(
            """
            INSERT INTO new_content_entity (contentEntityDbId, contentId, mediaType, listId, createdAt)
            SELECT CE.contentEntityDbId, CE.contentId, CE.mediaType, LE.listId, CE.createdAt
            FROM content_entity AS CE
            JOIN new_list_entity AS LE ON CE.listId = LE.listName
            """
        )

        // Drop the old tables
        db.execSQL("DROP TABLE content_entity")

        // Rename new tables to the official table names
        db.execSQL("ALTER TABLE new_list_entity RENAME TO list_entity")
        db.execSQL("ALTER TABLE new_content_entity RENAME TO content_entity")

        // List of default list names
        val defaultLists = listOf("watchlist", "watched")

        // Insert default lists if they do not exist
        defaultLists.forEach { listName ->
            db.execSQL(
                """
                INSERT INTO list_entity (listName)
                SELECT ? WHERE NOT EXISTS (
                    SELECT 1 FROM list_entity WHERE listName = ?
                )
                """,
                arrayOf(listName, listName)
            )
        }
    }
}
