package com.projects.moviemanager.database.repository

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.database.model.ContentEntity

interface DatabaseRepository {
    suspend fun insertItem(contentId: Int, mediaType: MediaType)

    suspend fun deleteItem(contentId: Int, mediaType: MediaType)

    suspend fun getAllItems(): List<ContentEntity>

    suspend fun searchItem(contentId: Int, mediaType: MediaType): ContentEntity?
}
