package com.projects.moviemanager.database.dao

import androidx.room.*
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.database.model.ContentEntity

@Dao
interface ContentEntityDao {
    @Query("SELECT * FROM content_entity")
    fun getAllItems(): List<ContentEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(itemEntity: ContentEntity)

    @Delete()
    fun delete(itemEntity: ContentEntity)

    @Query("SELECT * FROM content_entity WHERE contentId=:contentId AND mediaType = :mediaType")
    fun searchItem(contentId: Int, mediaType: String): ContentEntity?
}
