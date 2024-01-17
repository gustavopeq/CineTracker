package com.projects.moviemanager.database.dao

import androidx.room.*
import com.projects.moviemanager.database.model.ContentEntity

@Dao
interface ContentEntityDao {
    @Query("SELECT * FROM content_entity")
    fun getAllItems(): List<ContentEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contentEntity: ContentEntity)

    @Query("DELETE FROM content_entity WHERE contentId=:contentId AND mediaType = :mediaType")
    fun delete(contentId: Int, mediaType: String)

    @Query("SELECT * FROM content_entity WHERE contentId=:contentId AND mediaType = :mediaType")
    fun searchItem(contentId: Int, mediaType: String): ContentEntity?
}
