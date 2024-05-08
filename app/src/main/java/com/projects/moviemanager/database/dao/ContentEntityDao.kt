package com.projects.moviemanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.moviemanager.database.model.ContentEntity

@Dao
interface ContentEntityDao {
    @Query("SELECT * FROM content_entity WHERE listId = :listId ORDER BY createdAt DESC")
    fun getAllItems(listId: Int): List<ContentEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contentEntity: ContentEntity)

    @Query(
        "DELETE FROM content_entity " +
            "WHERE contentId=:contentId AND mediaType = :mediaType AND listId = :listId"
    )
    fun delete(contentId: Int, mediaType: String, listId: Int)

    @Query(
        "SELECT * FROM content_entity WHERE contentId = :contentId AND mediaType = :mediaType"
    )
    fun searchItems(contentId: Int, mediaType: String): List<ContentEntity>

    @Query(
        "SELECT * FROM content_entity WHERE " +
            "contentId = :contentId AND mediaType = :mediaType AND listId = :listId"
    )
    fun getItem(contentId: Int, mediaType: String, listId: Int): ContentEntity?
}
