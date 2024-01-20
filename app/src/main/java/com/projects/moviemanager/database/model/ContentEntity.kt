package com.projects.moviemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_entity")
data class ContentEntity(
    @PrimaryKey(autoGenerate = true) val contentEntityDbId: Int = 0,
    val contentId: Int,
    val mediaType: String,
    val listId: String
)
